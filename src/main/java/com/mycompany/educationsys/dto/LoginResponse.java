package com.mycompany.educationsys.dto;

import com.mycompany.educationsys.entity.Role;

public class LoginResponse {
    private String token;
    private String roleName;
    private String message;


    public LoginResponse(String token, String roleName, String message) {
        this.token = token;
        this.roleName = roleName;
        this.message = message;
    }

    public String getToken() { return token; }
    public String getRoleName() { return roleName; }
    public String getMessage() {
        return message;
    }
}
