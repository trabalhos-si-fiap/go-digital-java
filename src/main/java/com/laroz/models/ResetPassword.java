package com.laroz.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reset_password")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = "id")
public class ResetPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private String email;
    private LocalDateTime createdAt;

    public ResetPassword(String token, String email) {
        id = null;
        this.token = token;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

    public boolean isInvalid(){
        return LocalDateTime.now().isAfter(createdAt.plusMinutes(5));
    }
}