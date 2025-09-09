package com.laros.dtos.auth;

public record NewPassword(
        String code,
        String newPassword
) {
}
