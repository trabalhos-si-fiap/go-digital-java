package com.laroz.dtos.tasks;

import java.time.LocalDateTime;
import java.util.List;

public record CreateTask(
        String title,
        String description,
        List<Long> idMembers,
        LocalDateTime deadline,
        Boolean dueComplete
) {
}
