package com.planit.api.trips.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.planit.api.models.DestinationModel;
import com.planit.api.models.Users;

public record TripListDto(
        Long id,
        String name,
        LocalDateTime startDate,
        LocalDateTime endDate,
        List<String> baggageSuggestion,
        List<Users> participants,
        LocalDateTime createdAt,
        DestinationModel destination
) {
}
