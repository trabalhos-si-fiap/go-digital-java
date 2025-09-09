package com.laros.dtos.marketingCampaignResult;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CreateMarketingCampaignResult(
        Long campaignId,
        BigDecimal investment,
        BigDecimal costs,
        BigDecimal revenue
) {
}
