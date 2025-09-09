package com.laros.controllers;

import com.laros.dtos.tasks.CreateTask;
import com.laros.dtos.tasks.TaskResponse;
import com.laros.dtos.tasks.UpdateTask;
import com.laros.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> create(@RequestBody @Valid CreateTask request, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(request, authentication));
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponse>> list(
            @PageableDefault(size=10, sort = {"id"}) Pageable page,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) LocalDateTime deadline,
            @RequestParam(required = false) Boolean dueComplete,
            @RequestParam(required = false) Long campaignId,
            @RequestParam(required = false) List<Long> userIds
    ) {
        return ResponseEntity.ok(
                taskService.list(
                        page,
                        title,
                        description,
                        deadline,
                        dueComplete,
                        campaignId,
                        userIds
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> update(@PathVariable Long id, @RequestBody @Valid UpdateTask request) {
        return ResponseEntity.ok(taskService.update(id, request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
