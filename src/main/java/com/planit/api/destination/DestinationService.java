package com.planit.api.destination;

import com.planit.api.destination.dtos.CreateDestinationDto;
import com.planit.api.destination.dtos.DestinationFilterDto;
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

    public Page<DestinationModel> listDestinations(DestinationFilterDto filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DestinationModel> query = cb.createQuery(DestinationModel.class);
        Root<DestinationModel> root = query.from(DestinationModel.class);

        Predicate predicate = cb.conjunction();

        if (filter.typeIds() != null && !filter.typeIds().isEmpty()) {
            predicate = cb.and(predicate, root.get("type").get("id").in(filter.typeIds()));
        }

        if (filter.climateIds() != null && !filter.climateIds().isEmpty()) {
            predicate = cb.and(predicate, root.get("climate").get("id").in(filter.climateIds()));
        }

        if (filter.seasonIds() != null && !filter.seasonIds().isEmpty()) {
            predicate = cb.and(predicate, root.get("bestSeason").get("id").in(filter.seasonIds()));
        }

        query.where(predicate);
        query.orderBy(cb.asc(root.get("name")));

        List<DestinationModel> content = entityManager.createQuery(query)
                .setFirstResult(filter.page() * filter.size())
                .setMaxResults(filter.size())
                .getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<DestinationModel> countRoot = countQuery.from(DestinationModel.class);
        countQuery.select(cb.count(countRoot)).where(predicate);
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(content, PageRequest.of(filter.page(), filter.size()), total);
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
                .climate(climate)
                .bestSeason(season)
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
}
