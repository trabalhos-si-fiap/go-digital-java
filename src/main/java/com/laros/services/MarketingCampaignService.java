package com.laros.services;

import com.laros.dtos.marketingCampaign.CreateMarketingCampaign;
import com.laros.dtos.marketingCampaign.MarketingCampaignResponse;
import com.laros.dtos.marketingCampaign.UpdateMarketingCampaign;
import com.laros.enums.CampaignStatus;
import com.laros.models.*;
import com.laros.repositories.*;
import com.laros.specifications.MarketingCampaignSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class MarketingCampaignService {
    @Autowired
    private MarketingCampaignRepository marketingCampaignRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PlatformRepository platformRepository;

    @Autowired
    private CampaignTypeRepository campaignTypeRepository;

    @Transactional
    public MarketingCampaignResponse create(
            CreateMarketingCampaign createCampaign,
            Authentication authentication
    ) {

        User createdBy = (User) authentication.getPrincipal();

        Project project = projectRepository.findById(
                createCampaign.projectId()
        ).orElseThrow(EntityNotFoundException::new);

        Platform platform = platformRepository.findById(
                createCampaign.platformId()
        ).orElseThrow(EntityNotFoundException::new);

        CampaignType campaignType = campaignTypeRepository.findById(
                createCampaign.campaignTypeId()
        ).orElseThrow(EntityNotFoundException::new);


        var marketingCampaign = new MarketingCampaign(
                createCampaign,
                createdBy,
                project,
                platform,
                campaignType,
                new MarketingCampaignResult()
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

    @Transactional
    public MarketingCampaignResponse update(
            Long id, UpdateMarketingCampaign updateMarketingCampaign
    ) {

        Project project = null;
        CampaignType campaignType = null;
        Platform platform = null;

        var campaign = marketingCampaignRepository.findById(
                id
        ).orElseThrow(EntityNotFoundException::new);

        if (updateMarketingCampaign.projectId() != null) {
            project = projectRepository.findById(
                    updateMarketingCampaign.projectId()
            ).orElseThrow(EntityNotFoundException::new);
        }

        if (updateMarketingCampaign.campaignTypeId() != null) {
            campaignType = campaignTypeRepository.findById(
                    updateMarketingCampaign.campaignTypeId()
            ).orElseThrow(EntityNotFoundException::new);
        }

        if (updateMarketingCampaign.platformId() != null) {
            platform = platformRepository.findById(
                    updateMarketingCampaign.platformId()
            ).orElseThrow(EntityNotFoundException::new);
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

    @Transactional
    public void delete(Long id) {
        MarketingCampaign campaign = marketingCampaignRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        campaign.delete();
        marketingCampaignRepository.save(campaign);
    }

    public MarketingCampaignResponse getById(Long id) {
        return new MarketingCampaignResponse(
                marketingCampaignRepository
                        .findById(id)
                        .orElseThrow(EntityNotFoundException::new)
        );
    }
}
