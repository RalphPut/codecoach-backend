package com.switchfully.teamair.codecoach.security.external;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class SecuredUser {
    private String email;
    private String password;
    private List<GrantedAuthority> roles;

    public String getId() {
        return id;
    }

    private String id;

    public static SecuredUser externalAuthentication(){
        return new SecuredUser();
    }

    public SecuredUser withUsername(String email) {
        this.email = email;
        return this;
    }

    public SecuredUser withPassword(String password) {
        this.password = password;
        return this;
    }

    public SecuredUser withRoles(List<String> roles) {
        this.roles = roles.stream().map(role -> (GrantedAuthority)new SecurityRole(role)).toList();
        return this;
    }

    public SecuredUser withId(String id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<GrantedAuthority> getRoles() {
        return roles;
    }
}
