package com.laroz.dtos.project;

import java.time.LocalDate;
import java.util.List;

public record UpdateProject(
        Long id,
        List<Long> clientIds,
        List<Long> membersIds,
        Long managerId,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate
) {
}
