package com.planit.api.models;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SequenceGenerator(name = "seasons_id_seq", sequenceName = "seasons_id_seq", allocationSize = 1, schema = "planit")
@Table(name = "system", schema = "planit")
public class SystemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seasons_id_seq")
    private Long id;

    private String integrate_name;

    private String project_name;


}
