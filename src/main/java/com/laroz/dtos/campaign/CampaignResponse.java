package com.laroz.dtos.campaign;

import com.laroz.dtos.project.ProjectResponse;
import com.laroz.enums.CampaignStatus;
import com.laroz.models.CampaignType;
import com.laroz.models.MarketingCampaign;
import com.laroz.models.Platform;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CampaignResponse(
        Long id,
        String name,
        ProjectResponse project,
        BigDecimal inestment,
        Platform plataform,
        CampaignType campaignType,
        CampaignStatus campaignStatus,
        LocalDate startDate,
        LocalDate endDate
) {
    public CampaignResponse(MarketingCampaign mc) {
        this(
                mc.getId(),
                mc.getName(),
                new ProjectResponse(mc.getProject()),
                mc.getInvestment(),
                mc.getPlatform(),
                mc.getCampaignType(),
                mc.getStatus(),
                mc.getStartDate(),
                mc.getEndDate()
        )
    }
}
