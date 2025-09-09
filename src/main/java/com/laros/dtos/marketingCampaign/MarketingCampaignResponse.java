package com.laros.dtos.marketingCampaign;

import com.laros.dtos.project.ProjectResponse;
import com.laros.enums.CampaignStatus;
import com.laros.models.CampaignType;
import com.laros.models.MarketingCampaign;
import com.laros.models.Platform;

import java.time.LocalDateTime;

public record MarketingCampaignResponse(
        Long id,
        String name,
        ProjectResponse project,
        Platform platform,
        CampaignType campaignType,
        CampaignStatus campaignStatus,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
    public MarketingCampaignResponse(MarketingCampaign mc) {
        this(
                mc.getId(),
                mc.getName(),
                new ProjectResponse(mc.getProject()),
                mc.getPlatform(),
                mc.getCampaignType(),
                mc.getStatus(),
                mc.getStartDate(),
                mc.getEndDate()
        );
    }
}
