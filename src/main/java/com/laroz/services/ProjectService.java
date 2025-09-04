package com.laroz.services;

import com.laroz.dtos.project.CreateProject;
import com.laroz.dtos.project.ProjectResponse;
import com.laroz.dtos.project.UpdateProject;
import com.laroz.models.Client;
import com.laroz.models.Project;
import com.laroz.models.User;
import com.laroz.repositories.ClientRepository;
import com.laroz.repositories.ProjectRepository;
import com.laroz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    public ProjectResponse create(
            CreateProject createProject,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        List<Client> clients = clientRepository.findAllById(
                createProject.clientIds()
        );

        List<User> members = userRepository.findAllById(
                createProject.membersIds()
        );


        return new ProjectResponse(
                projectRepository.save(
                        new Project(user, clients, members, createProject)
                )
        );
    }

    public List<ProjectResponse> list(Pageable page) {
        return projectRepository.findByIsActiveTrue(page).map(ProjectResponse::new).toList();
    }


    public ProjectResponse update(
            UpdateProject updateProject,
            Authentication authentication
    ) {

        List<User> members = null;
        List<Client> clients = null;
        User manager = null;

        // Todo: Fazer controle de usuário permitido

        var project = projectRepository.getReferenceById(
                updateProject.id()
        );

        // Buscando os dados se foi enviado.

        if (updateProject.clientIds() != null) {
            clients = clientRepository.findAllById(
                    updateProject.clientIds()
            );
        }

        if (updateProject.membersIds() != null) {
            members = userRepository.findAllById(
                    updateProject.membersIds()
            );
        }

        if (updateProject.managerId() != null) {
            manager = userRepository.getReferenceById(
                    updateProject.managerId()
            );
        }

        project.update(updateProject, clients, members, manager);

        return new ProjectResponse(
                projectRepository.save(project)
        );

    }

    public void delete(Long id) {
        Project project = projectRepository.getReferenceById(id);
        project.delete();
        projectRepository.save(project);
    }
}
