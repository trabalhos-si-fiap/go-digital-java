package com.laroz.dtos.campaign;

import com.laroz.enums.CampaignStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdateMarketingCampaign(
        Long id,
        String name,
        Long projectId,
        BigDecimal investment,
        Long platformId,
        Long campaignTypeId,
        CampaignStatus campaignStatus,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
