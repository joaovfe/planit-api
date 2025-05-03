package com.planit.api.trips.dtos;

import java.util.List;

public record CreateTripDto(
        String name,
        Long userId,
        String departureDatetime,
        Long climatePreferenceId,
        Long seasonId,
        List<Long> destinationIds,
        List<Long> participantIds
) {}
