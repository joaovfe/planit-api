package com.planit.api.destination;

import java.util.List;

import org.springframework.stereotype.Service;

import com.planit.api.destination.dtos.CreateDestinationDto;
import com.planit.api.destination.dtos.DestinationDto;
import com.planit.api.models.DestinationModel;
import com.planit.api.models.DestinationTypeModel;
import com.planit.api.repositories.DestinationRepository;
import com.planit.api.repositories.DestinationTypeRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DestinationService {

        @PersistenceContext
        private EntityManager entityManager;

        private final DestinationRepository destinationRepository;
        private final DestinationTypeRepository typeRepository;

        public List<DestinationDto> listDestinations() {
                List<DestinationModel> destinations = destinationRepository.findAll();

                return destinations.stream().map(dest -> DestinationDto.builder()
                                .id(dest.getId())
                                .name(dest.getName())
                                .description(dest.getDescription())
                                .type(dest.getType())
                                .build()).toList();
        }

        @Transactional
        public DestinationModel createDestination(CreateDestinationDto dto) {
                DestinationTypeModel type = typeRepository.findById(dto.typeId())
                                .orElseThrow(() -> new IllegalArgumentException("Tipo não encontrado"));
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

        public DestinationModel getDestinationById(Long id) {
                return destinationRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Destino não encontrado"));
        }

        public DestinationModel getDestinationAndIncrementView(Long id) {
                DestinationModel destination = getDestinationById(id);

                destination.setViewCount(
                        destination.getViewCount() != null ? destination.getViewCount() + 1 : 1
                );

                return destinationRepository.save(destination);
        }
}
