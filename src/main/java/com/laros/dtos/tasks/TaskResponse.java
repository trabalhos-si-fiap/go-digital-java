package com.laros.dtos.tasks;

import com.laros.dtos.user.UserResponse;
import com.laros.models.Task;

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
