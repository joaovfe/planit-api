package com.planit.api.baggage.dtos;

import java.util.List;

public record CreateBaggageItemDto(
        String name,
        String category,
        Long climatePreferenceId,
        Long seasonId,
        String description
) {
}
