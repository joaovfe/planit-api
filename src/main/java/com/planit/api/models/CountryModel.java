package com.planit.api.models;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SequenceGenerator(name = "country_id_seq", sequenceName = "country_id_seq", allocationSize = 1, schema = "planit")
@Table(name = "country", schema = "planit")
public class CountryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_id_seq")
    private Long id;

    private String name;

    private String code;
}
