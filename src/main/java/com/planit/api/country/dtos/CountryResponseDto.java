package com.planit.api.country.dtos;


import lombok.Builder;

@Builder
public record CountryResponseDto(
        Long id,
        String name,
        String code
) {}