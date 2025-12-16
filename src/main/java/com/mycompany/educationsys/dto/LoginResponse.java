package com.mycompany.educationsys.dto;

import com.mycompany.educationsys.entity.Role;

import java.util.Optional;
import java.util.Set;

public class LoginResponse {
    private String token;
    private Set<Role> roles;


    public LoginResponse(String token, Set<Role> roles) {
        this.token = token;
        this.roles = roles;
    }

    public String getToken() { return token; }
    public Set<Role> getRoles() { return roles; }
}
