package com.laros.dtos.tasks;

import com.laros.interfaces.HasIdsMembers;

import java.time.LocalDateTime;
import java.util.List;

public record UpdateTask(
        String title,
        String description,
        List<Long> idMembers,
        LocalDateTime deadline,
        Boolean dueComplete

) implements HasIdsMembers {
}
