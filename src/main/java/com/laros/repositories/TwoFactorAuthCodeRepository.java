package com.laros.repositories;

import com.laros.models.TwoFactorAuthCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TwoFactorAuthCodeRepository extends JpaRepository<TwoFactorAuthCode, Long> {
    Optional<TwoFactorAuthCode> findTopByUserIdOrderByCreatedAtDesc(Long id);

    void deleteByExpiresInBefore(LocalDateTime now);
}
