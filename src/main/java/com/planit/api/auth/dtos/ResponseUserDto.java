package com.planit.api.auth.dtos;

import com.planit.api.enums.ERoleUser;

public record ResponseUserDto(
        String name,
        String email,
        String password_hash,
        ERoleUser role
) {}
