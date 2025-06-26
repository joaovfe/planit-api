package com.planit.api.reviews.dtos;

import java.math.BigDecimal;

// DTO ajustado para receber 'note' e 'moneySpent'
public record CreateReviewDto(
        int note,
        BigDecimal moneySpent
) {
}