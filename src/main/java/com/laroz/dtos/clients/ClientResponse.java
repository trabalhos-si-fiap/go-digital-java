package com.laroz.dtos.clients;

import com.laroz.models.Client;

public record ClientResponse(
        Long id,
        String name,
        String email,
        String instagram
) {
    public ClientResponse(Client save) {
        this(
                save.getId(), save.getName(), save.getEmail(), save.getInstagram()
        );
    }

}
