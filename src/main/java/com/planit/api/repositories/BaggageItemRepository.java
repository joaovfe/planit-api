package com.planit.api.repositories;

import com.planit.api.models.BaggageItemModel;
import com.planit.api.models.ClimateModel;
import com.planit.api.models.SeasonModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaggageItemRepository extends JpaRepository<BaggageItemModel, Long> {
    List<BaggageItemModel> findByClimatePreferenceAndSeason(ClimateModel climate, SeasonModel season);
}
