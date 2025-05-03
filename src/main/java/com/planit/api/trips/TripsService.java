package com.planit.api.trips;

import com.planit.api.trips.dtos.CreateTripDto;
import com.planit.api.trips.dtos.TripListDto;
import com.planit.api.models.*;
import com.planit.api.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripsService {

    private final TripRepository tripRepository;
    private final UserRepository usersRepository;
    private final ClimateRepository climateRepository;
    private final SeasonRepository seasonRepository;
    private final DestinationRepository destinationRepository;
    private final BaggageItemRepository baggageItemRepository;

    @Transactional
    public TripModel createTrip(CreateTripDto dto, LocalDateTime departureDatetime) {
        Users user = usersRepository.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        ClimateModel climate = climateRepository.findById(dto.climatePreferenceId())
                .orElseThrow(() -> new IllegalArgumentException("Clima não encontrado"));

        SeasonModel season = seasonRepository.findById(dto.seasonId())
                .orElseThrow(() -> new IllegalArgumentException("Estação não encontrada"));

        List<Users> participants = usersRepository.findAllById(dto.participantIds());

        List<DestinationModel> destinations = destinationRepository.findAllById(dto.destinationIds());

        List<BaggageItemModel> baggageItems = baggageItemRepository.findByClimatePreferenceAndSeason(climate, season);

        TripModel trip = TripModel.builder()
                .name(dto.name())
                .user(user)
                .climatePreference(climate)
                .season(season)
                .destinations(destinations)
                .departureDatetime(departureDatetime)
                .participants(participants)
                .baggageItems(baggageItems)
                .createdAt(LocalDateTime.now())
                .build();

        return tripRepository.save(trip);
    }

    public List<TripListDto> listTrips() {
        List<TripModel> trips = tripRepository.findAll();
        return trips.stream().map(trip -> new TripListDto(
                trip.getId(),
                trip.getName(),
                trip.getClimatePreference().getName(),
                trip.getSeason().getName(),
                trip.getCreatedAt(),
                trip.getDestinations().stream().map(DestinationModel::getName).collect(Collectors.toList())
        )).collect(Collectors.toList());
    }

    public TripListDto getTripById(Long id) {
        TripModel trip = tripRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Viagem não encontrada"));

        List<String> destinationNames = trip.getDestinations().stream()
                .map(dest -> dest.getName())
                .toList();

        return new TripListDto(
                trip.getId(),
                trip.getName(),
                trip.getClimatePreference().getName(),
                trip.getSeason().getName(),
                trip.getCreatedAt(),
                destinationNames
        );
    }

    public void updateTrip(Long id, CreateTripDto dto, LocalDateTime departureDatetime) {
        TripModel trip = tripRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Viagem não encontrada"));

        ClimateModel climate = climateRepository.findById(dto.climatePreferenceId())
                .orElseThrow(() -> new IllegalArgumentException("Clima não encontrado"));

        SeasonModel season = seasonRepository.findById(dto.seasonId())
                .orElseThrow(() -> new IllegalArgumentException("Estação não encontrada"));

        trip.setName(dto.name());
        trip.setDepartureDatetime(departureDatetime);
        trip.setClimatePreference(climate);
        trip.setSeason(season);

        tripRepository.save(trip);
    }

    public void deleteTrip(Long id) {
        TripModel trip = tripRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Viagem não encontrada"));

        tripRepository.delete(trip);
    }
}
