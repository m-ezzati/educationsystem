package com.mycompany.educationsys.dto;

import com.mycompany.educationsys.entity.Role;

import java.util.Optional;
import java.util.Set;

public class LoginResponse {
    private String token;
    private Set<Role> roles;
    private String message;


    public LoginResponse(String token, Set<Role> roles, String message) {
        this.token = token;
        this.roles = roles;
        this.message = message;
    }

    public String getToken() { return token; }
    public Set<Role> getRoles() { return roles; }
    public String getMessage() {
        return message;
    }
}
