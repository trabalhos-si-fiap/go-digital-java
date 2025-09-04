package com.laroz.controllers;

import com.laroz.dtos.campaign.CreateMarketingCampaign;
import com.laroz.dtos.campaign.CampaignResponse;
import com.laroz.dtos.campaign.UpdateCampaign;
import com.laroz.services.CampaignService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marketing-campaign")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    //CRUD
    @PostMapping
    public ResponseEntity<CampaignResponse> create(
            @RequestBody @Valid CreateMarketingCampaign createCampaign,
            Authentication authentication
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                campaignService.create(createCampaign, authentication)
        );
    }

    @GetMapping
    ResponseEntity<List<CampaignResponse>> list(
            @PageableDefault(size = 10, sort = {"id"}) Pageable page
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                campaignService.list(page)
        );
    }

    @PutMapping
    ResponseEntity<CampaignResponse> update(
            @RequestBody @Valid UpdateCampaign updateCampaign,
            Authentication authentication
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                campaignService.update(updateCampaign, authentication)
        );

    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        campaignService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
