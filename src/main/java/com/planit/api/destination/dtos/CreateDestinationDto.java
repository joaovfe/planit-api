package com.planit.api.destination.dtos;

public record CreateDestinationDto(
        String name,
        String description,
        Long typeId,
        Long climateId,
        Long seasonId
) {
}
