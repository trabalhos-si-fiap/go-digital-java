package com.laroz.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.laroz.enums.UserRole;
import com.laroz.infra.exceptions.InvalidOrExpiredTokenException;
import com.laroz.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TokenService {

    @Value("${spring.api.security.token.secret}")
    private String secret;
    private String issuer = "Larós";

    public String generateToken(User user){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return JWT.create()
                .withIssuer(issuer)
                .withClaim("roles", roles)
                .withClaim("id", user.getId())
                .withSubject(user.getUsername())
                .withExpiresAt(expiresAt())
                .sign(algorithm);
    }

    public String getSubject(String tokenJWT) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    // specify any specific claim validations
                    .withIssuer(issuer)
                    // reusable verifier instance
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new InvalidOrExpiredTokenException("Invalid or expired token.");
        }
    }

    private Instant expiresAt() {
        return LocalDateTime.now().plusHours(10).toInstant(ZoneOffset.of("-03:00"));
    }

    public static List<UserRole> getRoles(String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");

        DecodedJWT decode = JWT.decode(token);
        String roles = decode.getClaim("roles").asString();

        List<UserRole> rolesList = Arrays.stream(roles.split(","))
                .map(role -> role.replace("ROLE_", ""))
                .map(UserRole::valueOf)
                .toList();
        return rolesList;
    }
}