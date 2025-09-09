package com.laros.services;

import com.laros.dtos.project.CreateProject;
import com.laros.dtos.project.ProjectResponse;
import com.laros.dtos.project.UpdateProject;
import com.laros.models.Client;
import com.laros.models.Project;
import com.laros.models.User;
import com.laros.repositories.ClientRepository;
import com.laros.repositories.ProjectRepository;
import com.laros.repositories.UserRepository;
import com.laros.specifications.ProjectSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
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

    public Page<ProjectResponse> list(
            Pageable page,
            String name,
            String description,
            List<Long> clientsIds,
            List<Long> membersIds,
            Long managerId,
            LocalDateTime startDate,
            LocalDateTime endDate

    ) {
        // Inicializa a Specification com o filtro 'ativo'
        Specification<Project> spec = Specification.where(ProjectSpecification.isActive());

        // Adiciona condições dinamicamente
        if (name != null && !name.isEmpty()) {
            spec = spec.and(ProjectSpecification.hasName(name));
        }
        if (description != null && !description.isEmpty()) {
            spec = spec.and(ProjectSpecification.hasDescription(description));
        }
        if (clientsIds != null && !clientsIds.isEmpty()) {
            spec = spec.and(ProjectSpecification.hasClients(clientsIds));
        }
        if (membersIds != null && !membersIds.isEmpty()) {
            spec = spec.and(ProjectSpecification.hasTeamMembers(membersIds));
        }
        if (managerId != null) {
            spec = spec.and(ProjectSpecification.hasManager(managerId));
        }
        if (startDate != null) {
            spec = spec.and(ProjectSpecification.hasStartDate(startDate));
        }
        if (endDate != null) {
            spec = spec.and(ProjectSpecification.hasEndDate(endDate));
        }

        // Executa a consulta e retorna a lista paginada
        return projectRepository.findAll(spec, page).map(ProjectResponse::new);
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
