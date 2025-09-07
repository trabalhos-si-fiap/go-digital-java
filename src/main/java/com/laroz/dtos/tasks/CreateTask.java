package com.laroz.dtos.tasks;

import com.laroz.interfaces.HasIdsMembers;

import java.time.LocalDateTime;
import java.util.List;

public record CreateTask (
        String title,
        String description,
        List<Long> idMembers,
        Long campaignId,
        LocalDateTime deadline,
        Boolean dueComplete
)  implements HasIdsMembers{

}
