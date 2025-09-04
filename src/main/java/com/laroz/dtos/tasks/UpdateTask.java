package com.laroz.dtos.tasks;

import java.time.LocalDateTime;

public record UpdateTask(
        String title,
        String description,
        LocalDateTime deadline,
        Boolean dueComplete,
        Integer position

) {
}
