package com.planit.api.models;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SequenceGenerator(name = "destinations_id_seq", sequenceName = "destinations_id_seq", allocationSize = 1, schema = "planit")
@Table(name = "destinations", schema = "planit")
public class DestinationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "destinations_id_seq")
    private Integer id;

    private String name;

    private String country;

    private String description;

    @ManyToOne
    @JoinColumn(name = "climate_id")
    private ClimateModel climate;

    @ManyToOne
    @JoinColumn(name = "best_season_id")
    private SeasonModel bestSeason;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private DestinationTypeModel type;
}
