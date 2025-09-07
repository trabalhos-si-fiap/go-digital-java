package com.laroz.dtos.project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record CreateProject(
        List<Long> clientIds,
        List<Long> membersIds,
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
