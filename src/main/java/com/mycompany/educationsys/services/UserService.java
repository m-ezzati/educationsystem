package com.mycompany.educationsys.services;

import com.mycompany.educationsys.entity.User;

import java.util.List;

public interface UserService {
    User authenticate(String email, String password);

    void register(String email, String username, String password, String roleName);

    List<User> findAll();

    void approveUser(Long userId);
}
