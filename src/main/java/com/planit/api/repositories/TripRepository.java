package com.planit.api.repositories;

import com.planit.api.models.TripModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<TripModel, Long> {
    Page<TripModel> findByNameContainingIgnoreCase(String nome, Pageable pageable);
    boolean existsByUserId(Long userId);
}