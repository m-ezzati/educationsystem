package com.mycompany.educationsys.dto;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String roleName;

    // constructor
    public UserDTO(Long id, String username, String email, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roleName = role;
    }

    // getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getRole() { return roleName; }
}
