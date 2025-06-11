package com.laroz.dtos.auth;

public record NewPassword(
        String code,
        String newPassword
) {
}
