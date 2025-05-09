package com.planit.api.trips;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.planit.api.GoogleAI.GoogleAIService;
import com.planit.api.GoogleAI.dtos.TripSuggestionDto;
import com.planit.api.models.BaggageItemModel;
import com.planit.api.models.ClimateModel;
import com.planit.api.models.CountryModel;
import com.planit.api.models.DestinationModel;
import com.planit.api.models.SeasonModel;
import com.planit.api.models.TripModel;
import com.planit.api.models.Users;
import com.planit.api.repositories.BaggageItemRepository;
import com.planit.api.repositories.DestinationRepository;
import com.planit.api.repositories.TripRepository;
import com.planit.api.repositories.UserRepository;
import com.planit.api.trips.dtos.CreateTripDto;
import com.planit.api.trips.dtos.TripListDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripsService {

    private final TripRepository tripRepository;
    private final UserRepository usersRepository;
    private final DestinationRepository destinationRepository;
    private final BaggageItemRepository baggageItemRepository;
   private final GoogleAIService googleAIService;

    @Transactional
    public TripModel createTrip(CreateTripDto dto) {

        Users user = dto.user();

        List<Users> participants = dto.participants();

        DestinationModel destination = dto.destination();

        if (destination.getId() == null) {
                destination = destinationRepository.save(destination); }
        TripModel trip = TripModel.builder()
                .name(dto.name())
                .user(user)
                .destination(destination)
                .startDate(dto.startDate())
                .endDate(dto.endDate())
                .participants(participants)
                .createdAt(LocalDateTime.now())
                .build();

                
        trip = tripRepository.save(trip);

        List<String> baggageSuggestions = dto.baggageSuggestion(); 


        for (String suggestion : baggageSuggestions) {
            BaggageItemModel baggageItem = new BaggageItemModel();
            baggageItem.setDescription(suggestion); 
            baggageItem.setTrip(trip); 
    
            baggageItemRepository.save(baggageItem);
        }
    
        return trip;
    }

      public List<TripModel> listTrips(String search, int take, int skip) {
        var page = search != null && !search.isEmpty()
                ? tripRepository.findByNameContainingIgnoreCase(search, PageRequest.of(skip / take, take))
                : tripRepository.findAll(PageRequest.of(skip / take, take));

        List<TripModel> content = page.getContent();

        return content;
    }
public TripListDto getTripById(Long id) {
    TripModel trip = tripRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Viagem não encontrada"));

    List<BaggageItemModel> baggageItems = baggageItemRepository.findByTrip(trip);

    return new TripListDto(
            trip.getId(),
            trip.getName(),
            trip.getStartDate(),
            trip.getEndDate(),
            baggageItems.stream()
                        .map(BaggageItemModel::getDescription) 
                        .collect(Collectors.toList()),
            trip.getParticipants(),
            trip.getEndDate(),
            trip.getDestination()
    );
}
public void updateTrip(Long id, CreateTripDto dto) {
        TripModel trip = tripRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Viagem não encontrada"));
    
        trip.setName(dto.name());
        trip.setStartDate(dto.startDate());
        trip.setEndDate(dto.endDate());
        trip.setDestination(dto.destination());
        trip.setParticipants(dto.participants());
        trip.setUser(dto.user());
    
        updateBaggageSuggestions(trip, dto.baggageSuggestion());
    
        tripRepository.save(trip);
    }
    
    private void updateBaggageSuggestions(TripModel trip, List<String> newSuggestions) {
        List<BaggageItemModel> existingBaggageItems = trip.getBaggageItems();
    
        List<BaggageItemModel> updatedBaggageItems = newSuggestions.stream()
                .map(description -> {
                    return existingBaggageItems.stream()
                            .filter(item -> item.getDescription().equals(description))
                            .findFirst()
                            .orElse(new BaggageItemModel(null, trip, description)); 
                })
                .collect(Collectors.toList());
    
        trip.setBaggageItems(updatedBaggageItems);
    
        existingBaggageItems.removeAll(updatedBaggageItems); 
        baggageItemRepository.deleteAll(existingBaggageItems); 
    }

    public void deleteTrip(Long id) {
        TripModel trip = tripRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Viagem não encontrada"));

        tripRepository.delete(trip);
    }

 public TripSuggestionDto generateSuggestion(String email) {

    Users user = usersRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

    CountryModel country = user.getCountryDesired();
    ClimateModel climate = user.getClimatePreference();
    SeasonModel season = user.getSeasonPreference();

    List<DestinationModel> matchingDestinations = destinationRepository.findAll();

    StringBuilder destinationsText = new StringBuilder("Aqui estão algumas opções de destinos:\n");
    for (DestinationModel destination : matchingDestinations) {
        destinationsText.append(String.format("- %s (%s)\n",
        destination.getName(),
        destination.getCountry()
));
    }

    String prompt = String.format(
            "Você é um assistente especializado em viagens. O usuário tem interesse em visitar %s, prefere viajar em climas %s e gosta da estação %s. " +
            "Com base nessas preferências, aqui estão algumas opções de destinos que ele poderia explorar: %s" +
            "Por favor, recomende um desses destinos de forma atrativa e envolvente, em até 3 parágrafos curtos.",
            country.getName(),
            climate.getName(),
            season.getName(),
            destinationsText.toString()
        
    );

    String responseGoogle = googleAIService.sendPrompt(prompt);

    return new TripSuggestionDto(responseGoogle);
}
}
