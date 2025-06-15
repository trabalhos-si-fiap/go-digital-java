package com.laroz.dtos.clients;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateClient(
        @NotBlank
        String name,
        @Email
        String email,
        @NotBlank
        String instagram
) {
}
