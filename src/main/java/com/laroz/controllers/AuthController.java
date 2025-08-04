package com.laroz.controllers;

import com.laroz.dtos.auth.AuthData;
import com.laroz.dtos.mail.Email;
import com.laroz.dtos.auth.NewPassword;
import com.laroz.infra.security.TokenJWT;
import com.laroz.infra.security.TokenService;
import com.laroz.repositories.UserRepository;
import com.laroz.services.PasswordRecoveryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthData data) {

        var authToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var authentication = authManager.authenticate(authToken);

        var tokenJTW = new TokenJWT(
                tokenService.generateToken(
                        userRepository.findByEmail(data.email()).orElseThrow(
                                EntityNotFoundException::new
                        )
                )
        );

        return ResponseEntity.ok(tokenJTW);
    }

    @Transactional
    @PostMapping("/password-code")
    public ResponseEntity sendResetPasswordCode(@RequestBody Email dados) throws IOException, InterruptedException {
        passwordRecoveryService.sendPasswordResetToken(dados.email());
        return ResponseEntity.ok("Link para recuperação de senha enviado para o seu e-mail.");
    }

    @PostMapping("/reset-password")
    @Transactional
    public ResponseEntity resetPassword(@RequestBody NewPassword newPasswordDTO) {
        passwordRecoveryService.saveNewPassword(newPasswordDTO);
        return ResponseEntity.ok().build();
    }

}
