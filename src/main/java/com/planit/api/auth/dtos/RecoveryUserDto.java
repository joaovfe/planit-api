package com.planit.api.auth.dtos;

import com.planit.api.enums.ERoleUser;

import java.util.List;

public record RecoveryUserDto(
        Long id,
        String usuario,
        List<ERoleUser> roles
) {}
