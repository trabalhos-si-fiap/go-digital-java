package com.laroz.dtos.user;

import com.laroz.models.User;

public record UserResponse(
        Long id,
        String mame,
        String email
) {

    public UserResponse(User save) {
        this(save.getId(), save.getName(), save.getEmail());
    }
}
