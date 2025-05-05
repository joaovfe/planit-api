package com.planit.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planit.api.models.SystemModel;

@Repository
public interface SystemRepository extends JpaRepository<SystemModel, Long> {
}
