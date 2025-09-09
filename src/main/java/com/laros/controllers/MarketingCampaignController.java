package com.laros.controllers;

import com.laros.dtos.marketingCampaign.CreateMarketingCampaign;
import com.laros.dtos.marketingCampaign.MarketingCampaignResponse;
import com.laros.dtos.marketingCampaign.UpdateMarketingCampaign;
import com.laros.dtos.tasks.TaskResponse;
import com.laros.enums.CampaignStatus;
import com.laros.services.MarketingCampaignService;
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

    @GetMapping("/{id}")
    public ResponseEntity<MarketingCampaignResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(marketingCampaignService.getById(id));
    }
    @PutMapping("/{id}")
    ResponseEntity<MarketingCampaignResponse> update(
            @RequestBody @Valid UpdateMarketingCampaign updateMarketingCampaign,
            @PathVariable Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                marketingCampaignService.update(id, updateMarketingCampaign)
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
