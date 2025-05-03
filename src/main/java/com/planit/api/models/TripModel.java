package com.planit.api.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "trips", schema = "planit")
public class TripModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRIPS_ID_SEQ")
    @SequenceGenerator(name = "TRIPS_ID_SEQ", sequenceName = "TRIPS_ID_SEQ", allocationSize = 1, schema = "planit")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private String name;

    @ManyToOne
    @JoinColumn(name = "climate_preference_id")
    private ClimateModel climatePreference;

    @ManyToOne
    @JoinColumn(name = "season_id")
    private SeasonModel season;

    private LocalDateTime createdAt;
    private LocalDateTime departureDatetime;

    @ManyToMany
    @JoinTable(
            name = "trips_destinations",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "destination_id")
    )
    private List<DestinationModel> destinations;

    @ManyToMany
    @JoinTable(
            name = "trip_users",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Users> participants;

    @ManyToMany
    @JoinTable(
            name = "trips_baggage_items",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "baggage_item_id")
    )
    private List<BaggageItemModel> baggageItems;
}
