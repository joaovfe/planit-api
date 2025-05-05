package com.planit.api.baggage.dtos;

public record CreateBaggageItemDto(
        String name,
        String category,
        Long climatePreferenceId,
        Long seasonId,
        String description
) {
}
