package com.planit.api.repositories;

import com.planit.api.models.DestinationTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationTypeRepository extends JpaRepository<DestinationTypeModel, Long> {
}
