package com.laroz.dtos.clients;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateClient(
        @NotBlank
        String name,
        @Email
        String email,
        @NotBlank
        String instagram
) {
}
