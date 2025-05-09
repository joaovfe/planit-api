package com.planit.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "baggage_items", schema = "planit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaggageItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    @JsonBackReference
    private TripModel trip;

    @Column(length = 1000)
    private String description;
}
