package com.laroz.dtos.user;

import com.laroz.models.User;

public record UserResponse(
        Long id,
        String mame,
        String email,
        String phone
) {

    public UserResponse(User save) {
        this(save.getId(), save.getName(), save.getEmail(), save.getPhone());
    }
}
