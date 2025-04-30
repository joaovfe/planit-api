package com.planit.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.planit.api.enums.ERoleUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@SequenceGenerator(name = "ROLES_ID_SEQ", sequenceName = "ROLES_ID_SEQ", allocationSize = 1, schema = "planit")
@Table(name = "roles", schema = "planit")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLES_ID_SEQ")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERoleUser roleName;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<Users> users;
}