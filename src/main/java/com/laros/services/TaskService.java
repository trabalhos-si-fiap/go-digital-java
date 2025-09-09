package com.laros.services;

import com.laros.dtos.tasks.CreateTask;
import com.laros.dtos.tasks.TaskResponse;
import com.laros.dtos.tasks.UpdateTask;
import com.laros.interfaces.HasIdsMembers;
import com.laros.models.MarketingCampaign;
import com.laros.models.Task;
import com.laros.models.User;
import com.laros.repositories.MarketingCampaignRepository;
import com.laros.repositories.TaskRepository;
import com.laros.repositories.UserRepository;
import com.laros.specifications.TaskSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MarketingCampaignRepository marketingCampaignRepository;
    public TaskResponse create(CreateTask taskToCreate, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();

        MarketingCampaign campaign = marketingCampaignRepository.getReferenceById(
                taskToCreate.campaignId()
        );

        return new TaskResponse(
                taskRepository.save(
                        new Task(
                                taskToCreate,
                                userDetails,
                                makeMembers(taskToCreate),
                                campaign
                        )
                )
        );
    }

    private List<User> makeMembers(HasIdsMembers taskToCreate) {
        if (taskToCreate.idMembers() == null) {
            return new ArrayList<>();
        }
        return userRepository.findAllById(taskToCreate.idMembers()).stream().toList();
    }

    public Page<TaskResponse> list(
            Pageable page,
            String title,
            String description,
            LocalDateTime deadline,
            Boolean dueComplete,
            Long campaignId,
            List<Long> userIds
    ) {
        Specification<Task> spec = Specification.where(TaskSpecification.isActive());

        if (title != null) {
            spec = spec.and(TaskSpecification.hasTitle(title));
        }
        if (description != null) {
            spec = spec.and(TaskSpecification.hasDescription(description));
        }
        if (deadline != null) {
            spec = spec.and(TaskSpecification.hasDeadline(deadline));
        }
        if (dueComplete != null) {
            spec = spec.and(TaskSpecification.hasDueComplete(dueComplete));
        }
        if (campaignId != null) {
            spec = spec.and(TaskSpecification.hasCampaign(campaignId));
        }
        if (userIds != null && !userIds.isEmpty()) {
            spec = spec.and(TaskSpecification.hasUsers(userIds));
        }

        return taskRepository.findAll(spec, page).map(TaskResponse::new);
    }

    public TaskResponse getById(Long id) {
        return new TaskResponse(
                taskRepository.getReferenceById(id)
        );
    }

    public TaskResponse update(Long id, UpdateTask request) {
        var task = taskRepository.getReferenceById(id);

        List<User> users = makeMembers(request);

        task.update(request, users);
        taskRepository.save(task);
        return new TaskResponse(task);
    }

    public void delete(Long id) {
        var task = taskRepository.getReferenceById(id);
        task.delete();
        taskRepository.save(task);
    }
}
