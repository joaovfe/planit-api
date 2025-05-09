package com.planit.api.trips.dtos;

import com.planit.api.models.DestinationModel;
import com.planit.api.models.Users;

import java.time.LocalDateTime;
import java.util.List;

public record CreateTripDto(
        String name,
        Users user,
        DestinationModel destination,
        List<String> baggageSuggestion,
        LocalDateTime startDate,
        LocalDateTime endDate,
        List<Users> participants
) {}