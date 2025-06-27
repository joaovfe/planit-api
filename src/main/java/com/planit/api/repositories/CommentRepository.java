package com.planit.api.repositories;

import com.planit.api.models.CommentModel;
import com.planit.api.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentModel, Long> {

    // Busca todos os comentários de uma viagem
    List<CommentModel> findByTripId(Long tripId);

    // Busca um comentário pelo seu ID e pelo autor (para garantir a permissão de editar/deletar)
    Optional<CommentModel> findByIdAndUser(Long id, Users user);
}