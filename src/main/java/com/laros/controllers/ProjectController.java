package com.laros.controllers;

import com.laros.dtos.project.CreateProject;
import com.laros.dtos.project.ProjectResponse;
import com.laros.dtos.project.UpdateProject;
import com.laros.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    //CRUD
    @PostMapping
    public ResponseEntity<ProjectResponse> create(
            @RequestBody @Valid CreateProject createProject,
            Authentication authentication
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                projectService.create(createProject, authentication)
        );
    }

    @GetMapping
    ResponseEntity<Page<ProjectResponse>> list(
            @PageableDefault(size = 10, sort = {"id"}) Pageable page,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) List<Long> clientsIds,
            @RequestParam(required = false) List<Long> membersIds,
            @RequestParam(required = false) Long managerId,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate

    ) {
        return ResponseEntity.ok(
                projectService.list(
                        page,
                        name,
                        description,
                        clientsIds,
                        membersIds,
                        managerId,
                        startDate,
                        endDate
                )
        );
    }

    @PutMapping
    ResponseEntity<ProjectResponse> update(
            @RequestBody @Valid UpdateProject updateProject,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                projectService.update(updateProject, authentication)
        );

    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
