package com.laroz.services;

import com.laroz.dtos.campaign.CreateMarketingCampaign;
import com.laroz.dtos.campaign.MarketingCampaignResponse;
import com.laroz.dtos.campaign.UpdateMarketingCampaign;
import com.laroz.enums.CampaignStatus;
import com.laroz.models.*;
import com.laroz.repositories.*;
import com.laroz.specifications.MarketingCampaignSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class MarketingCampaignService {
    @Autowired
    private MarketingCampaignRepository marketingCampaignRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PlatformRepository platfomRepository;

    @Autowired
    private CampaignTypeRepository campaignTypeRepository;

    public MarketingCampaignResponse create(
            CreateMarketingCampaign createCampaign,
            Authentication authentication
    ) {

        User createdBy = (User) authentication.getPrincipal();

        Project project = projectRepository.getReferenceById(
                createCampaign.projectId()
        );

        Platform platform = platfomRepository.getReferenceById(
                createCampaign.platformId()
        );

        CampaignType campaignType = campaignTypeRepository.getReferenceById(
                createCampaign.campaignTypeId()
        );

        var marketingCampaign = new MarketingCampaign(
                createCampaign,
                createdBy,
                project,
                platform,
                campaignType
        );

        return new MarketingCampaignResponse(
                marketingCampaignRepository.save(marketingCampaign)
        );
    }

    public Page<MarketingCampaignResponse> list(
            Pageable page,
            String name,
            Long projectId,
            Long platformId,
            BigDecimal investment,
            Long campaignTypeId,
            CampaignStatus status,
            LocalDateTime startDate,
            LocalDateTime endDate

    ) {
        Specification<MarketingCampaign> spec = Specification.where(MarketingCampaignSpecification.isActive());

        if (name != null && !name.isEmpty()) {
            spec = spec.and(MarketingCampaignSpecification.hasName(name));
        }
        if (projectId != null) {
            spec = spec.and(MarketingCampaignSpecification.hasProject(projectId));
        }
        if (platformId != null) {
            spec = spec.and(MarketingCampaignSpecification.hasPlatform(platformId));
        }
        if (investment != null) {
            spec = spec.and(MarketingCampaignSpecification.hasInvestment(investment));
        }
        if (campaignTypeId != null) {
            spec = spec.and(MarketingCampaignSpecification.hasCampaignType(campaignTypeId));
        }
        if (status != null) {
            spec = spec.and(MarketingCampaignSpecification.hasStatus(status));
        }
        if (startDate != null) {
            spec = spec.and(MarketingCampaignSpecification.hasStartDate(startDate));
        }
        if (endDate != null) {
            spec = spec.and(MarketingCampaignSpecification.hasEndDate(endDate));
        }

        return marketingCampaignRepository.findAll(spec, page).map(MarketingCampaignResponse::new);
    }


    public MarketingCampaignResponse update(
            UpdateMarketingCampaign updateMarketingCampaign,
            Authentication authentication
    ) {

        Project project = null;
        CampaignType campaignType = null;
        Platform platform = null;

        // Todo: Fazer controle de usuário permitido

        var campaign = marketingCampaignRepository.getReferenceById(
                updateMarketingCampaign.id()
        );

        if (updateMarketingCampaign.projectId() != null) {
            project = projectRepository.getReferenceById(
                    updateMarketingCampaign.projectId()
            );
        }

        if (updateMarketingCampaign.campaignTypeId() != null) {
            campaignType = campaignTypeRepository.getReferenceById(
                    updateMarketingCampaign.campaignTypeId()
            );
        }

        if (updateMarketingCampaign.platformId() != null) {
            platform = platfomRepository.getReferenceById(
                    updateMarketingCampaign.platformId()
            );
        }

        campaign.update(
                updateMarketingCampaign,
                project,
                campaignType,
                platform
        );

        return new MarketingCampaignResponse(
                marketingCampaignRepository.save(campaign)
        );

    }

    public void delete(Long id) {
        MarketingCampaign campaign = marketingCampaignRepository.getReferenceById(id);
        campaign.delete();
        marketingCampaignRepository.save(campaign);
    }
}
