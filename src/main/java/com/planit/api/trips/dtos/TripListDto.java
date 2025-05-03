package com.planit.api.trips.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record TripListDto(
        Long id,
        String name,
        String climatePreference,
        String season,
        LocalDateTime createdAt,
        List<String> destinations
) {
}
