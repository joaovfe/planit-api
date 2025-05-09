package com.planit.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.planit.api.models.BaggageItemModel;
import com.planit.api.models.TripModel;

public interface BaggageItemRepository extends JpaRepository<BaggageItemModel, Long> {
     List<BaggageItemModel> findByTrip(TripModel trip);
}   
