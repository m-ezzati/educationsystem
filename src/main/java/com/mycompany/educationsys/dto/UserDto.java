package com.mycompany.educationsys.dto;

import com.mycompany.educationsys.entity.enums.UserStatus;

public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String roleName;
    private UserStatus status;

    public UserDto(Long id, String username, String email, String role, UserStatus status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roleName = role;
        this.status = status;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getRole() { return roleName; }
    public String getRoleName() {
        return roleName;
    }

    public UserStatus getStatus() {
        return status;
    }
}
