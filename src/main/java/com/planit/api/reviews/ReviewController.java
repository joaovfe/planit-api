package com.planit.api.reviews;

import com.planit.api.models.ReviewModel;
import com.planit.api.reviews.dtos.CreateReviewDto;
import com.planit.api.reviews.dtos.ReviewResponseDto;
import com.planit.api.reviews.dtos.UpdateReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/viagens/{tripId}/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(
            @PathVariable Long tripId,
            @RequestBody CreateReviewDto createReviewDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        ReviewModel newReview = reviewService.createReview(tripId, createReviewDto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(newReview));
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> getReviewsForTrip(@PathVariable Long tripId) {
        List<ReviewModel> reviews = reviewService.getReviewsForTrip(tripId);
        List<ReviewResponseDto> responseDtos = reviews.stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    @RestController
    @RequestMapping("/reviews")
    public class ReviewActionsController {

        @Autowired
        private ReviewService reviewService;

        @PutMapping("/{reviewId}")
        public ResponseEntity<ReviewResponseDto> updateReview(
                @PathVariable Long reviewId,
                @RequestBody UpdateReviewDto updateReviewDto,
                @AuthenticationPrincipal UserDetails userDetails) {

            ReviewModel updatedReview = reviewService.updateReview(reviewId, updateReviewDto, userDetails.getUsername());
            return ResponseEntity.ok(toDto(updatedReview));
        }

        @DeleteMapping("/{reviewId}")
        public ResponseEntity<Void> deleteReview(
                @PathVariable Long reviewId,
                @AuthenticationPrincipal UserDetails userDetails) {

            reviewService.deleteReview(reviewId, userDetails.getUsername());
            return ResponseEntity.noContent().build();
        }
    }

    // Método auxiliar atualizado para os novos campos
    private ReviewResponseDto toDto(ReviewModel review) {
        return new ReviewResponseDto(
                review.getId(),
                review.getNote(),
                review.getMoneySpent(),
                review.getTrip().getId(),
                review.getUser().getId(),
                review.getUser().getName(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }
}