package com.laros.dtos.auth;

public record TwoFactorData(
        String email,
        String code
) {
}
