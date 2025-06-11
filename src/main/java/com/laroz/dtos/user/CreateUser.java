package com.laroz.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateUser(
        @NotBlank
        String name,
        @Email
        String email,
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9\\s])[A-Za-z\\d[^\\s]]{8,}$")
        String password
) {
}
