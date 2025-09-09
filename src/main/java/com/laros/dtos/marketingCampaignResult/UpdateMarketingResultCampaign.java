package com.laros.dtos.marketingCampaignResult;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record UpdateMarketingResultCampaign(
        @NotBlank
        Long id,
        BigDecimal investment,
        BigDecimal costs,
        BigDecimal revenue,
        Boolean isActive
) {
}
