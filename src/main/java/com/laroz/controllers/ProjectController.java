package com.laroz.controllers;

import com.laroz.dtos.project.CreateProject;
import com.laroz.dtos.project.ProjectResponse;
import com.laroz.dtos.project.UpdateProject;
import com.laroz.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

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
    ResponseEntity<List<ProjectResponse>> list(
            @PageableDefault(size = 10, sort = {"id"}) Pageable page
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                projectService.list(page)
        );
    }

    @PutMapping
    ResponseEntity<ProjectResponse> update(
            @RequestBody @Valid UpdateProject updateProject,
            Authentication authentication
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
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
