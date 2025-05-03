package com.planit.api.destination.dtos;

import java.util.List;

public record DestinationFilterDto(
        List<Long> typeIds,
        List<Long> climateIds,
        List<Long> seasonIds,
        int page,
        int size
) {
}