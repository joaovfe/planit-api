package com.planit.api.auth.dtos;

public record LoginUserDto(
        String email,
        String password
) {}
