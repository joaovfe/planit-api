package com.planit.api.destination.dtos;


import lombok.Builder;

@Builder
public record DestinationRankingDto
        (Long id,
         String name,
         String country,
         String description,
         Integer viewCount,
         Integer favoriteCount,
         Integer ranking
         ) {
}
