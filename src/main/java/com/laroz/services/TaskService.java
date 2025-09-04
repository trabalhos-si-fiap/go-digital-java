package com.laroz.services;

import com.laroz.dtos.tasks.CreateTask;
import com.laroz.dtos.tasks.TaskResponse;
import com.laroz.dtos.tasks.UpdateTask;
import com.laroz.models.Task;
import com.laroz.models.User;
import com.laroz.repositories.TaskRepository;
import com.laroz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    public TaskResponse create(CreateTask taskToCreate, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();

        // Todo: Verificar qual é o card com maior posição e adicionar + 1 antes de salvar
        // Não pode existir 2 cards com a mesma posição.
        // Preciso salvar qual é a lista que ele esta para estão pesquisar qual é a posição que ele está?

        return new TaskResponse(
                taskRepository.save(
                        new Task(
                                taskToCreate,
                                userDetails,
                                makeMembers(taskToCreate)
                        )
                )
        );
    }

    private List<User> makeMembers(CreateTask taskToCreate) {
        var result = new ArrayList<User>();

        if (taskToCreate.idMembers() == null) {
            return result;
        }

        for (Long id : taskToCreate.idMembers()) {
            var user = userRepository.findById(id);
            user.ifPresent(result::add);
        }

        return result;
    }

    public Page<TaskResponse> list(Pageable page) {
        return taskRepository.findByIsActiveTrue(page).map(TaskResponse::new);
    }

    public TaskResponse getById(Long id) {
        return new TaskResponse(
                taskRepository.getReferenceById(id)
        );
    }

    public TaskResponse update(Long id, UpdateTask request) {
        var task = taskRepository.getReferenceById(id);
        task.update(request);
        taskRepository.save(task);
        return new TaskResponse(task);
    }

    public void delete(Long id) {
        var task = taskRepository.getReferenceById(id);
        task.delete();
        taskRepository.save(task);
    }
}
