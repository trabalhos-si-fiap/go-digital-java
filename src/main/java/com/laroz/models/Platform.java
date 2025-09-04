package com.laroz.models;

import com.laroz.dtos.platform.CreatePlatform;
import com.laroz.dtos.platform.UpdatePlatform;
import jakarta.persistence.*;
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
@Table(name = "platforms")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = "id")
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(nullable = false)
    private boolean isActive = true;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Platform(CreatePlatform createPlatafom) {
        this.name = createPlatafom.name();
    }

    public void update(UpdatePlatform updatePlatform) {
        if (updatePlatform.name() != null) {
            this.name = updatePlatform.name();
        }
    }

    public void delete() {
        this.isActive = false;
    }
}
