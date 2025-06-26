package com.planit.api.repositories;

import com.planit.api.destination.dtos.DestinationRankingDto;
import com.planit.api.models.DestinationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface DestinationRepository extends JpaRepository<DestinationModel, Long> {
    interface DestinationRankingProjection {
        Long getId();
        String getName();
        String getCountry();
        String getDescription();
        Integer getViewCount();
        Integer getFavoriteCount();
        Integer getRanking();
    }

    @Query(value = "SELECT d.id, d.name, d.country, d.description, d.view_count as viewCount, d.favorite_count as favoriteCount, ROW_NUMBER() OVER (ORDER BY d.view_count DESC) as ranking FROM planit.destinations d", nativeQuery = true)
    List<DestinationRankingProjection> findAllWithViewCountRanking();

    @Query(value = "SELECT d.id, d.name, d.country, d.description, d.view_count as viewCount, d.favorite_count as favoriteCount, ROW_NUMBER() OVER (ORDER BY d.favorite_count DESC) as ranking FROM planit.destinations d", nativeQuery = true)
    List<DestinationRankingProjection> findAllWithFavoriteCountRanking();
}
