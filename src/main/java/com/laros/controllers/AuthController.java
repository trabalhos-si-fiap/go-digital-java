package com.laros.controllers;

import com.laros.dtos.auth.AuthData;
import com.laros.dtos.auth.TwoFactorData;
import com.laros.dtos.mail.Email;
import com.laros.dtos.auth.NewPassword;
import com.laros.infra.security.TokenJWT;
import com.laros.infra.security.TokenService;
import com.laros.repositories.UserRepository;
import com.laros.services.PasswordRecoveryService;
import com.laros.services.TwoFactorAuthService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private PasswordRecoveryService passwordRecoveryService;

    @Autowired
    private TwoFactorAuthService twoFactorAuthService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthData data) throws IOException, InterruptedException {

        var authToken = new UsernamePasswordAuthenticationToken(
                data.email(),
                data.password()
        );
        authManager.authenticate(authToken);
        twoFactorAuthService.generateAndSend2FACode(data.email());

        return ResponseEntity.ok("Código 2FA enviado para seu e-mail.");
    }

    @PostMapping("/validate-2fa")
    public ResponseEntity<String> validate2FA(@RequestBody @Valid TwoFactorData twoFactorData) {
        var user = userRepository.findByEmail(
                twoFactorData.email()
        ).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (twoFactorAuthService.validate2FACode(user, twoFactorData.code())) {

            var tokenJTW = new TokenJWT(
                    tokenService.generateToken(
                        user
                    )
            );

            return ResponseEntity.ok(tokenJTW.token());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Código 2FA inválido ou expirado.");
    }

    @Transactional
    @PostMapping("/password-code")
    public ResponseEntity<String> sendResetPasswordCode(@RequestBody Email dados) throws IOException, InterruptedException {
        passwordRecoveryService.sendPasswordResetToken(dados.email());
        return ResponseEntity.ok("Link para recuperação de senha enviado para o seu e-mail.");
    }

    @PostMapping("/reset-password")
    @Transactional
    public ResponseEntity<Void> resetPassword(@RequestBody NewPassword newPasswordDTO) {
        passwordRecoveryService.saveNewPassword(newPasswordDTO);
        return ResponseEntity.ok().build();
    }

}
