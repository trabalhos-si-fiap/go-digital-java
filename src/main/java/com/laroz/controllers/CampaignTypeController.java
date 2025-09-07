package com.laroz.controllers;

import com.laroz.dtos.campaignType.CreateCampaignType;
import com.laroz.dtos.campaignType.UpdateCampaignType;
import com.laroz.models.CampaignType;
import com.laroz.services.CampaignTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campaign-type")
public class CampaignTypeController {

    @Autowired
    private CampaignTypeService campaignTypeService;

    //CRUD
    @PostMapping
    public ResponseEntity<CampaignType> create(
            @RequestBody @Valid CreateCampaignType createCampaignType,
            Authentication authentication
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                campaignTypeService.create(createCampaignType, authentication)
        );
    }

    @GetMapping
    ResponseEntity<Page<CampaignType>> list(
            @PageableDefault(size = 10, sort = {"id"}) Pageable page,
            @RequestParam(required = false) String name
    ) {
        return ResponseEntity.ok(
                campaignTypeService.list(page, name)
        );
    }

    @PutMapping
    ResponseEntity<CampaignType> update(
            @RequestBody @Valid UpdateCampaignType updateCampaignType,
            Authentication authentication
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                campaignTypeService.update(updateCampaignType, authentication)
        );

    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        campaignTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
