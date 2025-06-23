package com.planit.api.models;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SequenceGenerator(
        name = "destination_favorites_id_seq",
        sequenceName = "destination_favorites_id_seq",
        allocationSize = 1,
        schema = "planit"
)
@Table(
        name = "destination_favorites",
        schema = "planit",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "destination_id"})
)
public class DestinationFavoriteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "destination_favorites_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private DestinationModel destination;

    @Column(name = "created_at")
    private java.time.LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = java.time.LocalDateTime.now();
    }
}
