package com.laroz.infra.security;

import com.laroz.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;

public class RoleGrantedAuthority implements GrantedAuthority {
    private final String authority;

    public RoleGrantedAuthority(UserRole role) {
        this.authority = "ROLE_" + role.name();
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
