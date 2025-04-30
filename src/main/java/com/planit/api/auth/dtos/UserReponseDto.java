package com.planit.api.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.planit.api.models.Role;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserReponseDto {
    private Long id;
    private String name;
    private String email;
    private List<Role> roles;
}
