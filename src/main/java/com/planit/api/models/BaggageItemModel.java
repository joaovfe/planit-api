package com.planit.api.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "baggage_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaggageItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @ManyToOne
    @JoinColumn(name = "climate_preference_id", referencedColumnName = "id")
    private ClimateModel climatePreference;

    @ManyToOne
    @JoinColumn(name = "season_id", referencedColumnName = "id")
    private SeasonModel season;

    @Column(length = 1000)
    private String description;
}
