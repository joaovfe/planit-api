package com.planit.api.destination;

import java.util.List;
import java.util.Optional;

import com.planit.api.destination.dtos.DestinationRankingDto;
import com.planit.api.enums.EVisualizationType;
import com.planit.api.models.DestinationFavoriteModel;
import com.planit.api.models.Users;
import org.springframework.stereotype.Service;

import com.planit.api.destination.dtos.CreateDestinationDto;
import com.planit.api.destination.dtos.DestinationDto;
import com.planit.api.models.DestinationModel;
import com.planit.api.models.DestinationTypeModel;
import com.planit.api.repositories.DestinationRepository;
import com.planit.api.repositories.DestinationTypeRepository;
import com.planit.api.repositories.DestinationFavoriteRepository;

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
        private final DestinationFavoriteRepository favoriteRepository;

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

        @Transactional
        public String favorite(Long destinationId, Users user) {
                DestinationModel destination = destinationRepository.findById(destinationId)
                        .orElseThrow(() -> new IllegalArgumentException("Destino não encontrado"));

                boolean alreadyFavorited = favoriteRepository.existsByUserAndDestination(user, destination);

                if (alreadyFavorited) {
                        return "Este destino já está favoritado.";
                }

                DestinationFavoriteModel favorite = DestinationFavoriteModel.builder()
                        .user(user)
                        .destination(destination)
                        .build();

                favoriteRepository.save(favorite);

                destination.setFavoriteCount(
                        destination.getFavoriteCount() != null ? destination.getFavoriteCount() + 1 : 1
                );
                destinationRepository.save(destination);

                return "Destino favoritado com sucesso!";
        }

        @Transactional
        public String unfavorite(Long destinationId, Users user) {
                DestinationModel destination = getDestinationById(destinationId);

                Optional<DestinationFavoriteModel> favoriteOpt = favoriteRepository.findByUserAndDestination(user, destination);

                if (favoriteOpt.isEmpty()) {
                        return "Este destino não está favoritado por este usuário.";
                }

                DestinationFavoriteModel favorite = favoriteOpt.get();

                if (!favorite.getUser().getId().equals(user.getId())) {
                        throw new SecurityException("Você não pode desfavoritar um destino favoritado por outro usuário.");
                }

                favoriteRepository.delete(favorite);

                Integer currentCount = destination.getFavoriteCount() != null ? destination.getFavoriteCount() : 0;
                destination.setFavoriteCount(Math.max(0, currentCount - 1));
                destinationRepository.save(destination);

                return "Destino removido dos favoritos com sucesso!";
        }

        public List<DestinationRankingDto> getDestinationsByRanking(EVisualizationType visualizationType) {
                List<DestinationRepository.DestinationRankingProjection> projections =
                        (visualizationType == EVisualizationType.FAVORITE)
                                ? destinationRepository.findAllWithFavoriteCountRanking()
                                : destinationRepository.findAllWithViewCountRanking();

                return projections.stream()
                        .map(p -> new DestinationRankingDto(
                                p.getId(),
                                p.getName(),
                                p.getCountry(),
                                p.getDescription(),
                                p.getViewCount(),
                                p.getFavoriteCount(),
                                p.getRanking()
                        ))
                        .toList();
        }

}
