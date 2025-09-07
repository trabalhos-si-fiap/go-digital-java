package com.laroz.models;

import com.laroz.dtos.project.CreateProject;
import com.laroz.dtos.project.UpdateProject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = "id")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "clients_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "aclient_id")
    )
    private List<Client> clients;

    @ManyToMany
    @JoinTable(
            name = "users_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> teamMembers;

    @ManyToOne
    @JoinColumn(name = "manager_id") // A Foreign Key estará em 'projects'
    private User manager;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<MarketingCampaign> campaigns;
    @Column(nullable = false)
    private boolean isActive = true;
    private LocalDateTime startDate;
    private LocalDateTime endDate;


    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Project(
            User manager,
            List<Client> clients,
            List<User> members,
            CreateProject createProject
    ) {
        name = createProject.name();
        description = createProject.description();
        startDate = createProject.startDate();
        endDate = createProject.endDate();

        this.teamMembers = members;
        this.clients = clients;
        this.manager = manager;
    }

    public Project update(
            UpdateProject updateProject,
            List<Client> clients,
            List<User> members,
            User manager
    ) {
        if (updateProject.name() != null) {
            this.name = updateProject.name();
        }

        if (updateProject.description() != null) {
            this.description = updateProject.description();
        }

        if (updateProject.startDate() != null) {
            this.startDate = updateProject.startDate();
        }

        if (updateProject.endDate() != null) {
            this.endDate = updateProject.endDate();
        }

        if (clients != null) {
            this.clients = clients;
        }

        if (members != null) {
            this.teamMembers = members;
        }

        if (manager != null) {
            this.manager = manager;
        }

        return this;
    }

    public void delete() {
        this.isActive = false;
    }
}
