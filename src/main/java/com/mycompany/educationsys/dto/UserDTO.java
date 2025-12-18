package com.mycompany.educationsys.dto;

import com.mycompany.educationsys.entity.enums.UserStatus;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String roleName;
    private boolean isApproved;

    // constructor
    public UserDTO(Long id, String username, String email, String role, UserStatus status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roleName = role;
        if(status == UserStatus.APPROVED){
            this.isApproved = true;
        }else {
            this.isApproved =false;
        }
    }

    // getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getRole() { return roleName; }
    public String getRoleName() {
        return roleName;
    }
    public boolean isApproved() {
        return isApproved;
    }
}
