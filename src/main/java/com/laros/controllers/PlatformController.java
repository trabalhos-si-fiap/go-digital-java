package com.laros.controllers;

import com.laros.dtos.platform.CreatePlatform;
import com.laros.dtos.platform.UpdatePlatform;
import com.laros.models.Platform;
import com.laros.services.PlatformService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/platform")
public class PlatformController {

    @Autowired
    private PlatformService platformService;

    //CRUD
    @PostMapping
    public ResponseEntity<Platform> create(
            @RequestBody @Valid CreatePlatform createPlatform,
            Authentication authentication
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                platformService.create(
                        createPlatform,
                        authentication
                )
        );
    }

    @GetMapping
    ResponseEntity<Page<Platform>> list(
            @PageableDefault(size = 10, sort = {"id"}) Pageable page,
            @RequestParam(required = false) String name
    ) {
        return ResponseEntity.ok(
                platformService.list(page, name)
        );
    }

    @PutMapping
    ResponseEntity<Platform> update(
            @RequestBody @Valid UpdatePlatform updatePlatform,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                platformService.update(updatePlatform, authentication)
        );

    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        platformService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
