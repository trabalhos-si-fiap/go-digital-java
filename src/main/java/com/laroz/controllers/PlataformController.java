package com.laroz.controllers;

import com.laroz.dtos.platform.CreatePlatform;
import com.laroz.dtos.platform.UpdatePlatform;
import com.laroz.models.Platform;
import com.laroz.services.PlatformService;
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
@RequestMapping("/api/plataform")
public class PlataformController {

    @Autowired
    private PlatformService platformService;

    //CRUD
    @PostMapping
    public ResponseEntity<Platform> create(
            @RequestBody @Valid CreatePlatform createPlataform,
            Authentication authentication
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                platformService.create(
                        createPlataform,
                        authentication
                )
        );
    }

    @GetMapping
    ResponseEntity<List<Platform>> list(
            @PageableDefault(size = 10, sort = {"id"}) Pageable page
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                platformService.list(page)
        );
    }

    @PutMapping
    ResponseEntity<Platform> update(
            @RequestBody @Valid UpdatePlatform updatePlataform,
            Authentication authentication
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                platformService.update(updatePlataform, authentication)
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
