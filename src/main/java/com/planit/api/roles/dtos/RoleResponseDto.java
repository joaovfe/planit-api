package com.planit.api.roles.dtos;


import com.planit.api.enums.ERoleUser;

import lombok.Builder;

@Builder
public record RoleResponseDto(
        Long id,
        ERoleUser roleName
) {}