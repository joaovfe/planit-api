package com.planit.api.comments;

import com.planit.api.models.CommentModel;
import com.planit.api.models.TripModel;
import com.planit.api.models.Users;
import com.planit.api.repositories.CommentRepository;
import com.planit.api.repositories.TripRepository;
import com.planit.api.repositories.UserRepository;
import com.planit.api.comments.dtos.CreateCommentDto;
import com.planit.api.comments.dtos.UpdateCommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CommentService {

    @Autowired private CommentRepository commentRepository;
    @Autowired private TripRepository tripRepository;
    @Autowired private UserRepository userRepository;

    public CommentModel createComment(Long tripId, CreateCommentDto dto, String userEmail) {
        Users user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        TripModel trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Viagem não encontrada"));

        // Regra: Usuário só pode comentar na sua viagem (se for participante)
        boolean isParticipant = trip.getParticipants().stream().anyMatch(p -> p.getId().equals(user.getId()));
        if (!isParticipant) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não pode comentar em uma viagem da qual não participa.");
        }

        CommentModel comment = CommentModel.builder()
                .comment(dto.comment())
                .trip(trip)
                .user(user)
                .build();

        return commentRepository.save(comment);
    }

    public List<CommentModel> getCommentsForTrip(Long tripId) {
        if (!tripRepository.existsById(tripId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Viagem não encontrada");
        }
        return commentRepository.findByTripId(tripId);
    }

    public CommentModel updateComment(Long commentId, UpdateCommentDto dto, String userEmail) {
        Users user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        // Regra: Usuário só pode editar seus comentários
        CommentModel comment = commentRepository.findByIdAndUser(commentId, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Comentário não encontrado ou você não tem permissão para editá-lo."));

        comment.setComment(dto.comment());
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId, String userEmail) {
        Users user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        // Regra: Usuário só pode deletar seus comentários
        CommentModel comment = commentRepository.findByIdAndUser(commentId, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Comentário não encontrado ou você não tem permissão para deletá-lo."));

        commentRepository.delete(comment);
    }
}