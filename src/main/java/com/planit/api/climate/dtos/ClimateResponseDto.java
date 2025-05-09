package com.planit.api.climate.dtos;


import lombok.Builder;

@Builder
public record ClimateResponseDto(
        Long id,
        String name
) {}