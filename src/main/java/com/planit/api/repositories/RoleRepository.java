package com.planit.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planit.api.models.RoleModel;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long> {
}
