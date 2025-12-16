package com.mycompany.educationsys.controller;

import com.mycompany.educationsys.dto.LoginRequest;
import com.mycompany.educationsys.dto.LoginResponse;
import com.mycompany.educationsys.entity.User;
import com.mycompany.educationsys.security.JwtService;
import com.mycompany.educationsys.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        User user = userService.authenticate(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        String token = jwtService.generateToken(user);

        return new LoginResponse(token, user.getRoles());
    }
}
