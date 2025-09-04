package com.laroz.dtos.project;

import com.laroz.dtos.clients.ClientResponse;
import com.laroz.dtos.user.UserResponse;
import com.laroz.models.Project;

import java.time.LocalDate;
import java.util.List;

public record ProjectResponse(
        Long id,
        String name,
        String description,
        List<ClientResponse> clients,
        List<UserResponse> teamMembers,
        UserResponse manager,
        LocalDate startDate,
        LocalDate endDate,
        Boolean isActive


) {
    public ProjectResponse(Project p) {
        this(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getClients().stream().map(ClientResponse::new).toList(),
                p.getTeamMembers().stream().map(UserResponse::new).toList(),
                new UserResponse(p.getManager()),
                p.getStartDate(),
                p.getEndDate(),
                p.isActive()
        );
    }
}
