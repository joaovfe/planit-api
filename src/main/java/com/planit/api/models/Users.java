package com.planit.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@SequenceGenerator(name = "USERS_ID_SEQ", sequenceName = "USERS_ID_SEQ", allocationSize = 1, schema = "planit")
@Table(name = "users", schema = "planit")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_ID_SEQ")
    @Column(name = "id")
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"), schema = "planit")
    private List<Role> roles;

    @Column(name = "email")
    private String email;

    @Column(name = "password_hash")
    private String password_hash;

    @JsonIgnore
    @OneToMany(mappedBy = "participants")
    private List<TripModel> tripsAsParticipant;

    @Column(name = "name")
    private String name;
}
