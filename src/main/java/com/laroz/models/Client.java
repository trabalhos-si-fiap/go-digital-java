package com.laroz.models;


import com.laroz.dtos.clients.CreateClient;
import com.laroz.dtos.clients.UpdateClient;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = "id")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @Column(unique = true)
    @Email
    private String email;
    @NotBlank
    private String instagram;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private boolean isActive;


    public Client(CreateClient request) {
        name = request.name();
        email = request.email();
        instagram = request.instagram();
        isActive = true;
    }

    public void update(UpdateClient request) {
        if (request.name() != null) {
            name = request.name();
        }
        if (request.email() != null) {
            email = request.email();
        }
        if (request.instagram() != null) {
            instagram = request.instagram();
        }
    }

    public void delete() {
        isActive = false;
    }
}
