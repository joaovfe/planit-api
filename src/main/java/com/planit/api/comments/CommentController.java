package com.planit.api.comments;

import com.planit.api.models.CommentModel;
import com.planit.api.comments.dtos.CreateCommentDto;
import com.planit.api.comments.dtos.UpdateCommentDto;
import com.planit.api.comments.dtos.CommentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    // --- Rotas aninhadas a viagens ---
    @PostMapping("/viagens/{tripId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long tripId,
            @RequestBody CreateCommentDto dto,
            @AuthenticationPrincipal UserDetails userDetails) {

        CommentModel newComment = commentService.createComment(tripId, dto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(newComment));
    }

    @GetMapping("/viagens/{tripId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getCommentsForTrip(@PathVariable Long tripId) {
        List<CommentModel> comments = commentService.getCommentsForTrip(tripId);
        List<CommentResponseDto> response = comments.stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // --- Rotas diretas para comentários ---
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody UpdateCommentDto dto,
            @AuthenticationPrincipal UserDetails userDetails) {

        CommentModel updatedComment = commentService.updateComment(commentId, dto, userDetails.getUsername());
        return ResponseEntity.ok(toDto(updatedComment));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetails userDetails) {

        commentService.deleteComment(commentId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    // Método auxiliar para conversão
    private CommentResponseDto toDto(CommentModel comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getComment(),
                comment.getTrip().getId(),
                comment.getUser().getId(),
                comment.getUser().getName(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}