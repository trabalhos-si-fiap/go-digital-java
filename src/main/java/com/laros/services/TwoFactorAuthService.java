package com.laros.services;

import com.laros.dtos.mail.EmailMessage;
import com.laros.interfaces.EmailSender;
import com.laros.models.TwoFactorAuthCode;
import com.laros.models.User;
import com.laros.repositories.TwoFactorAuthCodeRepository;
import com.laros.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class TwoFactorAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private TwoFactorAuthCodeRepository twoFactorAuthCodeRepository;

    public void generateAndSend2FACode(String email) throws IOException, InterruptedException {
        log.debug("Generate Password called");
        var user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        var twoFactorCode = new TwoFactorAuthCode(user);
        String codeFormatted = twoFactorCode.getCodeFormatted();
        twoFactorAuthCodeRepository.save(twoFactorCode);

        if (user.getEmail().equals("admin@laros.com")) {
            log.warn("First Admin User 2FA code: " + codeFormatted + " Please change password and email.");
            return;
        }

        String message = "Seu código de autenticação é: " + codeFormatted + ". Ele expirará em 5 minutos.";
        emailSender.sendEmail(
                new EmailMessage.Builder()
                        .subject("2FA - Larós MKT [Não Responda]")
                        .senderName("Larós MKT Digital - No reply")
                        .senderMail("laros.mkt-naoresponda@gmail.com")
                        .addTo(new EmailMessage.Recipient(user.getName(), user.getEmail()))
                        .textContent(message)
                        .build()
        );
    }

    public boolean validate2FACode(User user, String code) {

        Optional<TwoFactorAuthCode> optionalSavedCode;
        optionalSavedCode = twoFactorAuthCodeRepository.findTopByUserIdOrderByCreatedAtDesc(user.getId());

        if (optionalSavedCode.isEmpty()) {
            log.debug("Código não encontrado.");
            return false;
        }

        if (optionalSavedCode.get().isInValid()) {
            log.debug("Código inválido.");
            return false;
        }

        TwoFactorAuthCode authCode =  optionalSavedCode.get();

        if (authCode.validateCode(code)) {
            log.debug("Código válido.");
            twoFactorAuthCodeRepository.delete(authCode);
            return true;
        }

        return false;
    }
}
