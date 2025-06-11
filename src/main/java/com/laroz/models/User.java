package com.laroz.models;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import com.laroz.dtos.user.CreateUser;
import com.laroz.dtos.user.UpdateUser;
import com.laroz.enums.UserRole;
import com.laroz.infra.security.RoleGrantedAuthority;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3, max = 35, message = "O atributo 'name' deve conter no minimo 3 e no máximo 35 caracteres")
    private String name;
    @Email
    @Column(unique = true)
    private String email;
    @Pattern(
            regexp="^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9\\s])[A-Za-z\\d[^\\s]]{8,}$",
            message="A Senha deve conter no minimo 8 caracteres, uma letra maiúscula e um caractere especial."
    )
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean isActive;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public User(CreateUser data) {
        name = data.name();
        email = data.email();
        password = data.password();
        role = UserRole.CUSTOMER;
        isActive = true;
    }

    public void update(UpdateUser data) {
        if (data.name() != null) {
            name = data.name();
        }

        if (data.email() != null) {
            name = data.email();
        }

        if (data.password() != null) {
            password = data.password();
        }
    }

    public void delete() {
        isActive = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new RoleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
