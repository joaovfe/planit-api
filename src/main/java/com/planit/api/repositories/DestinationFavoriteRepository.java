package com.planit.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planit.api.models.DestinationFavoriteModel;
import com.planit.api.models.DestinationModel;
import com.planit.api.models.Users;

@Repository
public interface DestinationFavoriteRepository extends JpaRepository<DestinationFavoriteModel, Long> {

    boolean existsByUserAndDestination(Users user, DestinationModel destination);

    Optional<DestinationFavoriteModel> findByUserAndDestination(Users user, DestinationModel destination);
}
