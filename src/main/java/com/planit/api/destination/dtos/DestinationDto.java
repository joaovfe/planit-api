package com.planit.api.destination.dtos;


import com.planit.api.models.DestinationTypeModel;
import lombok.Builder;

@Builder
public record DestinationDto(
        Long id,
        String name,
        String description,
        DestinationTypeModel type
) {}