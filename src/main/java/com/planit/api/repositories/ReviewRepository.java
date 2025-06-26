package com.planit.api.repositories;

import com.planit.api.models.ReviewModel;
import com.planit.api.models.TripModel;
import com.planit.api.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewModel, Long> {

    List<ReviewModel> findByTripId(Long tripId);

    Optional<ReviewModel> findByTripAndUser(TripModel trip, Users user);

    // Este método é a chave para a melhoria!
    Optional<ReviewModel> findByIdAndUser(Long id, Users user);
}