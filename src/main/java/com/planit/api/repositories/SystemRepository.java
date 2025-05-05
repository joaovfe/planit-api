package com.planit.api.repositories;

import com.planit.api.models.SeasonModel;
import com.planit.api.models.SystemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemRepository extends JpaRepository<SystemModel, Long> {
}
