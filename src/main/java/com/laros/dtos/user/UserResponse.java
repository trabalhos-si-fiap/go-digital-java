package com.laros.dtos.user;

import com.laros.models.User;

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
