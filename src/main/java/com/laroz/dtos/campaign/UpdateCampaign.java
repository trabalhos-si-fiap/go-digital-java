package com.laroz.dtos.campaign;

import com.laroz.enums.CampaignStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateCampaign(
        Long id,
        String name,
        Long projectId,
        BigDecimal inestment,
        Long plataformId,
        Long campaignTypeId,
        CampaignStatus campaignStatus,
        LocalDate startDate,
        LocalDate endDate
) {
}
