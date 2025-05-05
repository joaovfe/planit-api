package com.planit.api.destination;

import com.planit.api.destination.dtos.CreateDestinationDto;
import com.planit.api.destination.dtos.DestinationDto;
import com.planit.api.models.*;
import com.planit.api.repositories.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DestinationService {

    @PersistenceContext
    private EntityManager entityManager;


    private final DestinationRepository destinationRepository;
    private final DestinationTypeRepository typeRepository;
    private final ClimateRepository climateRepository;
    private final SeasonRepository seasonRepository;

    public List<DestinationDto> listDestinations() {
        List<DestinationModel> destinations = destinationRepository.findAll();

        return destinations.stream().map(dest -> DestinationDto.builder()
                .id(dest.getId())
                .name(dest.getName())
                .description(dest.getDescription())
                .type(dest.getType())
                .build()
        ).toList();
    }
    @Transactional
    public DestinationModel createDestination(CreateDestinationDto dto) {
        DestinationTypeModel type = typeRepository.findById(dto.typeId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo não encontrado"));

        ClimateModel climate = climateRepository.findById(dto.climateId())
                .orElseThrow(() -> new IllegalArgumentException("Clima não encontrado"));

        SeasonModel season = seasonRepository.findById(dto.seasonId())
                .orElseThrow(() -> new IllegalArgumentException("Estação não encontrada"));

        DestinationModel destination = DestinationModel.builder()
                .name(dto.name())
                .description(dto.description())
                .type(type)
                .build();

        return destinationRepository.save(destination);
    }

    public void updateDestination(Long id, CreateDestinationDto dto) {
        DestinationModel model = destinationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Destino não encontrado"));

        model.setName(dto.name());
        model.setDescription(dto.description());

        destinationRepository.save(model);
    }

    public void deleteDestination(Long id) {
        DestinationModel trip = destinationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Viagem não encontrada"));

        destinationRepository.delete(trip);
    }
}
