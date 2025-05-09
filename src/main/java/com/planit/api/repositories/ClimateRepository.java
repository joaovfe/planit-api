package com.planit.api.repositories;

import com.planit.api.models.ClimateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClimateRepository extends JpaRepository<ClimateModel, Long> {
}
