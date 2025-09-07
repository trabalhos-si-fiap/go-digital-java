package com.laroz.specifications;

import com.laroz.models.MarketingCampaign;
import com.laroz.models.Task;
import com.laroz.models.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;


import java.time.LocalDateTime;
import java.util.List;

public class TaskSpecification {

    public static Specification<Task> hasTitle(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Task> hasDescription(String description) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + description.toLowerCase() + "%");
    }

    public static Specification<Task> hasDeadline(LocalDateTime deadline) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("deadline"), deadline);
    }

    public static Specification<Task> hasDueComplete(Boolean dueComplete) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("dueComplete"), dueComplete);
    }

    public static Specification<Task> hasCampaign(Long campaignId) {
        return (root, query, criteriaBuilder) -> {
            Join<Task, MarketingCampaign> campaignJoin = root.join("campaign");
            return criteriaBuilder.equal(campaignJoin.get("id"), campaignId);
        };
    }

    public static Specification<Task> hasUsers(List<Long> userIds) {
        return (root, query, criteriaBuilder) -> {
            Join<Task, User> usersJoin = root.join("members");
            return usersJoin.get("id").in(userIds);
        };
    }

    public static Specification<Task> isActive() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isTrue(root.get("isActive"));
    }
}
