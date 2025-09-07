package com.laroz.dtos.clients;

import com.laroz.models.Client;
import jakarta.validation.constraints.NotBlank;

public record ClientResponse(
        Long id,
        String name,
        String email,
        String instagram,
        String phone
) {
    public ClientResponse(Client save) {
        this(
                save.getId(), save.getName(), save.getEmail(), save.getInstagram(), save.getPhone()
        );
    }

}
