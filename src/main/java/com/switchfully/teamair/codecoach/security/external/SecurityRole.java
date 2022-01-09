package com.switchfully.teamair.codecoach.security.external;

import org.springframework.security.core.GrantedAuthority;

public class SecurityRole implements GrantedAuthority {
    private final String role;

    public SecurityRole(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
