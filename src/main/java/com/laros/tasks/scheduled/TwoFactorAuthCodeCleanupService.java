package com.laros.tasks.scheduled;

import com.laros.repositories.TwoFactorAuthCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class TwoFactorAuthCodeCleanupService {

    @Autowired
    private TwoFactorAuthCodeRepository twoFactorAuthCodeRepository;

    @Scheduled(fixedRate = 3600000) // Executa a cada hora (em milissegundos)
    public void cleanupExpiredCodes() {
        twoFactorAuthCodeRepository.deleteByExpiresInBefore(LocalDateTime.now());
        log.debug("Registros expirados removidos.");
    }
}
