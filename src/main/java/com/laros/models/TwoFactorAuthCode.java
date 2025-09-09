package com.laros.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.stream.Collectors;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "2fa_codes")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = "id")
public class TwoFactorAuthCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false, length = 6)
    private String code;
    private static final int CODE_EXPIRATION_MINUTES = 5;
    private LocalDateTime expiresIn = LocalDateTime.now().plusMinutes(CODE_EXPIRATION_MINUTES);

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public TwoFactorAuthCode(User user) {
        this.user = user;
        generateCode();
    }

    private void generateCode() {
        this.code = String.format("%06d", new SecureRandom().nextInt(100000));
    }

    public String getCodeFormatted() {
        if (this.code == null) {
            generateCode();
        }
        return code.substring(0, 3) + "-" + code.substring(3);
    }

    public Boolean isValid() {
        return LocalDateTime.now().isBefore(expiresIn);
    }

    public Boolean isInValid() {
        return !isValid();
    }

    public boolean validateCode(String userCode) {
        return this.code.equals(
                clean(userCode)
        );
    }

    private String clean(String userCode) {
        return userCode.chars()
                .filter(Character::isDigit)
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
    }
}
