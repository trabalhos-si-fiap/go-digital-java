package com.laroz.dtos.tasks;

import com.laroz.dtos.user.UserResponse;
import com.laroz.models.Task;

import java.util.List;

public record TaskResponse(
        Long id,
        String title,
        String description,
        UserResponse createdBy,
        Boolean isActive,
        Boolean dueComplete,
        List<UserResponse> teamMembers
        ) {

    public TaskResponse(Task task) {
        this(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                new UserResponse(task.getCreatedBy()),
                task.isActive(),
                task.isDueComplete(),
                task.getMembers().stream().map(UserResponse::new).toList()
        );
    }
}
