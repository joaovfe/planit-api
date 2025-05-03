package com.planit.api.models;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SequenceGenerator(name = "destination_types_id_seq", sequenceName = "destination_types_id_seq", allocationSize = 1, schema = "planit")
@Table(name = "destination_types", schema = "planit")
public class DestinationTypeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "destination_types_id_seq")
    private Integer id;

    private String name;
}
