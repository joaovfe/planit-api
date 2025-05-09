package com.planit.api.repositories;

import com.planit.api.models.DestinationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepository extends JpaRepository<DestinationModel, Long> {
}
