package com.laros.controllers;

import com.laros.dtos.campaignType.CreateCampaignType;
import com.laros.dtos.campaignType.UpdateCampaignType;
import com.laros.models.CampaignType;
import com.laros.services.CampaignTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/campaign-type")
public class CampaignTypeController {

    @Autowired
    private CampaignTypeService campaignTypeService;

    //CRUD
    @PostMapping
    public ResponseEntity<CampaignType> create(
            @RequestBody @Valid CreateCampaignType createCampaignType
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                campaignTypeService.create(createCampaignType)
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

    @GetMapping("/{id}")
    public ResponseEntity<CampaignType> getById(@PathVariable Long id) {
        return ResponseEntity.ok(campaignTypeService.getById(id));
    }

    @PutMapping("/{id}")
    ResponseEntity<CampaignType> update(
            @RequestBody @Valid UpdateCampaignType updateCampaignType,
            @PathVariable Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                campaignTypeService.update(id, updateCampaignType)
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
