package com.laros.dtos.marketingCampaignResult;

import com.laros.models.MarketingCampaignResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MarketingCampaignResultResponse(
        Long id,
        Long marketingCampaignId,
        BigDecimal investment,
        BigDecimal costs,
        BigDecimal revenue,
        BigDecimal netProfit,
        BigDecimal roi,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public MarketingCampaignResultResponse(MarketingCampaignResult mc) {
        this(
                mc.getId(),
                mc.getMarketingCampaign().getId(),
                mc.getInvestment(),
                mc.getCosts(),
                mc.getRevenue(),
                mc.getNetProfit(),
                mc.getRoi(),
                mc.getCreatedAt(),
                mc.getUpdatedAt()
        );
    }
}
