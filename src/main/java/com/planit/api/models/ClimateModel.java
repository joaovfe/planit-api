package com.planit.api.models;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SequenceGenerator(name = "climates_id_seq", sequenceName = "climates_id_seq", allocationSize = 1, schema = "planit")
@Table(name = "climates", schema = "planit")
public class ClimateModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "climates_id_seq")
    private Integer id;

    private String name;
}
