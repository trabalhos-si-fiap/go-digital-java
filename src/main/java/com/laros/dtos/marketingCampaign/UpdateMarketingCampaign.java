package com.laros.dtos.marketingCampaign;

import com.laros.enums.CampaignStatus;

import java.time.LocalDateTime;

public record UpdateMarketingCampaign(
        Long id,
        String name,
        Long projectId,
        //BigDecimal investment,
        Long platformId,
        Long campaignTypeId,
        CampaignStatus campaignStatus,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
