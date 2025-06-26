package com.planit.api.reviews;

import com.planit.api.models.ReviewModel;
import com.planit.api.models.TripModel;
import com.planit.api.models.Users;
import com.planit.api.repositories.ReviewRepository;
import com.planit.api.repositories.TripRepository;
import com.planit.api.repositories.UserRepository;
import com.planit.api.reviews.dtos.CreateReviewDto;
import com.planit.api.reviews.dtos.UpdateReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    public ReviewModel createReview(Long tripId, CreateReviewDto createReviewDto, String userEmail) {
        Users user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        TripModel trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Viagem não encontrada"));

        boolean isParticipant = trip.getParticipants().stream().anyMatch(p -> p.getId().equals(user.getId()));
        if (!isParticipant) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não pode avaliar uma viagem da qual não participou.");
        }

        reviewRepository.findByTripAndUser(trip, user).ifPresent(r -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Você já fez uma avaliação para esta viagem.");
        });

        ReviewModel review = ReviewModel.builder()
                .note(createReviewDto.note()) // Atualizado
                .moneySpent(createReviewDto.moneySpent()) // Atualizado
                .trip(trip)
                .user(user)
                .build();

        return reviewRepository.save(review);
    }

    public List<ReviewModel> getReviewsForTrip(Long tripId) {
        if (!tripRepository.existsById(tripId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Viagem não encontrada");
        }
        return reviewRepository.findByTripId(tripId);
    }

    public ReviewModel updateReview(Long reviewId, UpdateReviewDto updateReviewDto, String userEmail) {
        Users user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        ReviewModel review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avaliação não encontrada"));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para editar esta avaliação.");
        }

        review.setNote(updateReviewDto.note()); // Atualizado
        review.setMoneySpent(updateReviewDto.moneySpent()); // Atualizado

        return reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId, String userEmail) {
        Users user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        ReviewModel review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avaliação não encontrada"));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para deletar esta avaliação.");
        }

        reviewRepository.delete(review);
    }
}