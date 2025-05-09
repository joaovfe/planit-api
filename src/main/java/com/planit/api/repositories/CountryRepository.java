package com.planit.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planit.api.models.CountryModel;

@Repository
public interface CountryRepository extends JpaRepository<CountryModel, Long> {
}
