package com.laroz.repositories;

import com.laroz.models.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {
    Optional<ResetPassword> findByToken(String code);
}