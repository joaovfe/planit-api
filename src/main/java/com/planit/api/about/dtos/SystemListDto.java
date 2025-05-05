package com.planit.api.about.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record SystemListDto(
        String project_name,
        String integrate_name
) {
}
