package com.planit.api.models;

import com.planit.api.enums.ERoleUser;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SequenceGenerator(name = "role_id_seq", sequenceName = "role_id_seq", allocationSize = 1, schema = "planit")
@Table(name = "roles", schema = "planit")
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERoleUser roleName;
}
