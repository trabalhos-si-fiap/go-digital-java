package com.laroz.controllers;

import com.laroz.dtos.campaign.CreateMarketingCampaign;
import com.laroz.dtos.campaign.MarketingCampaignResponse;
import com.laroz.dtos.campaign.UpdateMarketingCampaign;
import com.laroz.enums.CampaignStatus;
import com.laroz.services.MarketingCampaignService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/marketing-campaign")
public class MarketingCampaignController {

    @Autowired
    private MarketingCampaignService marketingCampaignService;

    @PostMapping
    public ResponseEntity<MarketingCampaignResponse> create(
            @RequestBody @Valid CreateMarketingCampaign createCampaign,
            Authentication authentication
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                marketingCampaignService.create(createCampaign, authentication)
        );
    }

    @GetMapping
    ResponseEntity<Page<MarketingCampaignResponse>> list(
            @PageableDefault(size = 10, sort = {"id"}) Pageable page,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) Long platformId,
            @RequestParam(required = false) BigDecimal investment,
            @RequestParam(required = false) Long campaignTypeId,
            @RequestParam(required = false) CampaignStatus status,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate
    ) {
        return ResponseEntity.ok(
                marketingCampaignService.list(
                        page,
                        name,
                        projectId,
                        platformId,
                        investment,
                        campaignTypeId,
                        status,
                        startDate,
                        endDate
                )
        );
    }

    @PutMapping
    ResponseEntity<MarketingCampaignResponse> update(
            @RequestBody @Valid UpdateMarketingCampaign updateMarketingCampaign,
            Authentication authentication
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                marketingCampaignService.update(updateMarketingCampaign, authentication)
        );

    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        marketingCampaignService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
