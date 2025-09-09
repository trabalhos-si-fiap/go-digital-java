package com.laros.controllers;

import com.laros.dtos.marketingCampaign.CreateMarketingCampaign;
import com.laros.dtos.marketingCampaign.MarketingCampaignResponse;
import com.laros.dtos.marketingCampaign.UpdateMarketingCampaign;
import com.laros.dtos.marketingCampaignResult.CreateMarketingCampaignResult;
import com.laros.dtos.marketingCampaignResult.MarketingCampaignResultResponse;
import com.laros.dtos.marketingCampaignResult.UpdateMarketingResultCampaign;
import com.laros.dtos.tasks.TaskResponse;
import com.laros.enums.CampaignStatus;
import com.laros.services.MarketingCampaignResultService;
import com.laros.services.MarketingCampaignService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@PreAuthorize("hasRole('ADM')")
@RestController
@RequestMapping("/api/marketing-campaign-result")
public class MarketingCampaignResultController {

    @Autowired
    private MarketingCampaignResultService marketingCampaignResultService;

    @PostMapping
    public ResponseEntity<MarketingCampaignResultResponse> create(
            @RequestBody @Valid CreateMarketingCampaignResult createCampaignResult
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                marketingCampaignResultService.create(createCampaignResult)
        );
    }

    @GetMapping
    ResponseEntity<Page<MarketingCampaignResultResponse>> list(
            @PageableDefault(size = 10, sort = {"id"}) Pageable page
    ) {
        return ResponseEntity.ok(
                marketingCampaignResultService.list(
                        page
                )
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<MarketingCampaignResultResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(marketingCampaignResultService.getById(id));
    }
    @PutMapping("/{id}")
    ResponseEntity<MarketingCampaignResultResponse> update(
            @RequestBody @Valid UpdateMarketingResultCampaign updateMarketingCampaign,
            @PathVariable Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                marketingCampaignResultService.update(id, updateMarketingCampaign)
        );

    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        marketingCampaignResultService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
