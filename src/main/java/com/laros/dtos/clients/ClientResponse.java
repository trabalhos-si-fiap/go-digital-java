package com.laros.dtos.clients;

import com.laros.models.Client;

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
