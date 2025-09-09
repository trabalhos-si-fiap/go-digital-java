package com.laros.dtos.project;

import java.time.LocalDateTime;
import java.util.List;

public record UpdateProject(
        List<Long> clientIds,
        List<Long> membersIds,
        Long managerId,
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
