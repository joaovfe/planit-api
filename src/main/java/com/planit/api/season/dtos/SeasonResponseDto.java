package com.planit.api.season.dtos;


import lombok.Builder;

@Builder
public record SeasonResponseDto(
        Long id,
        String name
) {}