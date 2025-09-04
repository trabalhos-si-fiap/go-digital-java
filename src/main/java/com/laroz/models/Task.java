package com.laroz.models;

import com.laroz.dtos.tasks.CreateTask;
import com.laroz.dtos.tasks.UpdateTask;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = "id")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDateTime deadline;
    private boolean dueComplete = false;
    private Integer position;

    @Column(nullable = false)
    private boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "campaign_id") // Relacionamento unidirecional com Campanha
    private MarketingCampaign campaign;

    @OneToMany
    private List<Comments> comments;

    @ManyToMany
    @JoinTable(
            name = "task_user",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;



    public Task(CreateTask data, User createdBy, List<User> members){
        title = data.title();
        description = data.description();
        // campaign = data.campaign();
        comments = new ArrayList<>();
        users = members;
        this.createdBy = createdBy;
    }

    public void update(UpdateTask request) {
        if (request.title() != null) {
            this.title = request.title();
        }

        if (request.description() != null) {
            this.description = request.description();
        }

        if (request.deadline() != null) {
            this.deadline = request.deadline();
        }

        if (request.dueComplete() != null) {
            this.dueComplete = request.dueComplete();
        }

        if (request.position() != null) {
            this.position = request.position();
        }
    }

    public void delete() {
        isActive = false;
    }
}
