package com.planit.api.models;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

        @ManyToOne
        @JoinColumn(name = "destination_id")
        private DestinationModel destination;

        private String name;

        private LocalDateTime createdAt;

        @JoinColumn(name = "start_date")
        private LocalDateTime startDate;

        @JoinColumn(name = "end_date")
        private LocalDateTime endDate;

        @ManyToMany
        @JoinTable(name = "trip_users", schema = "planit", joinColumns = @JoinColumn(name = "trip_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
        private List<Users> participants;

        @OneToMany(mappedBy = "trip", fetch = FetchType.EAGER)
        @JsonManagedReference
        private List<BaggageItemModel> baggageItems;
}
