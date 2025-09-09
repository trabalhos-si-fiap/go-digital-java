package com.laros.services;

import com.laros.dtos.mail.EmailMessage;
import com.laros.dtos.auth.NewPassword;
import com.laros.infra.exceptions.InvalidPasswordCodeException;
import com.laros.infra.security.CryptService;
import com.laros.interfaces.EmailSender;
import com.laros.models.ResetPassword;
import com.laros.models.User;
import com.laros.repositories.ResetPasswordRepository;
import com.laros.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class PasswordRecoveryService {

    @Autowired
    private CryptService cryptService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResetPasswordRepository resetPasswordRepository;

    @Autowired
    private EmailSender emailSender;

    public void sendPasswordResetToken(String email) throws IOException, InterruptedException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        String uuidToken = UUID.randomUUID().toString();

        resetPasswordRepository.save(
                new ResetPassword(uuidToken, user.getEmail())
        );

        var emailRequest = getEmailMessage(user, uuidToken);

        emailSender.sendEmail(emailRequest);
    }

    private static EmailMessage getEmailMessage(User user, String token) {
        var emailRequest = new EmailMessage();

        emailRequest.setSender(new EmailMessage.Sender("[Larós] Não responda", "resgate.ja@gmail.com"));
        emailRequest.setTo(List.of(new EmailMessage.Recipient("Usuário", user.getEmail())));
        emailRequest.setSubject("[Larós] Recuperação de senha.");
        emailRequest.setHtmlContent("Seu código de recuperação de senha é:\n %s\nEsse código é valido por apenas 5 minutos.".formatted(token));
        emailRequest.setReplyTo(new EmailMessage.ReplyTo("laros@gmail.com", "Não responda"));
        return emailRequest;
    }

    public void saveNewPassword(NewPassword newPasswordDTO) {
        ResetPassword code = resetPasswordRepository.findByToken(newPasswordDTO.code())
                .orElseThrow(
                        () -> new InvalidPasswordCodeException("Código inválido.")
                );

        if(code.isInvalid()) {
            resetPasswordRepository.delete(code);
            throw new InvalidPasswordCodeException("Código expirado.");
        }

        User user = userRepository.findByEmail(code.getEmail()).orElseThrow(
                () -> {
                    resetPasswordRepository.delete(code);
                    new EntityNotFoundException("Usuário não encontrado.");
                    return null;
                }
        );

        user.setPassword(cryptService.encode(
                        newPasswordDTO.newPassword()
                )
        );

        userRepository.save(user);
        resetPasswordRepository.delete(code);
    }
}