package com.laros.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.security.SecureRandom;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.stream.Collectors;


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

    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false, length = 6)
    private String code;
    private LocalDateTime expiresIn;
    @Transient
    private Clock clock = Clock.systemDefaultZone();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    private static final int CODE_EXPIRATION_MINUTES = 5;
    private static final String CODE_FORMAT = "%06d";

    public TwoFactorAuthCode(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.user = user;
        generateCode();
    }

    public TwoFactorAuthCode(User user, Clock clock) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (clock == null) {
            throw new IllegalArgumentException("Clock cannot be null");
        }

        this.clock = clock;
        this.user = user;
        generateCode();
    }

    private void generateCode() {
        this.code = String.format(CODE_FORMAT, new SecureRandom().nextInt(100000));
        this.expiresIn = LocalDateTime.now(clock).plusMinutes(CODE_EXPIRATION_MINUTES);
    }

    public String getCodeFormatted() {
        if (this.code == null) {
            generateCode();
        }
        return code.substring(0, 3) + "-" + code.substring(3);
    }

    public boolean isValid() {
        return LocalDateTime.now(clock).isBefore(expiresIn);
    }

    public boolean isInValid() {
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
