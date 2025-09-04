package com.laroz.dtos.project;

import java.time.LocalDate;
import java.util.List;

public record CreateProject(
        List<Long> clientIds,
        List<Long> membersIds,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate
) {
}
