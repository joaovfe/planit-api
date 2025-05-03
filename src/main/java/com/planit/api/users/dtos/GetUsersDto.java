package com.planit.api.users.dtos;

import com.planit.api.models.Role;
import java.util.List;

public record GetUsersDto(Long id, String nome, String email, List<Role> roles) {}