package com.mycompany.educationsys.controller;

import com.mycompany.educationsys.dto.LoginRequest;
import com.mycompany.educationsys.dto.LoginResponse;
import com.mycompany.educationsys.dto.RegisterRequest;
import com.mycompany.educationsys.dto.ResisterResponse;
import com.mycompany.educationsys.entity.User;
import com.mycompany.educationsys.security.JwtService;
import com.mycompany.educationsys.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            User user = userService.authenticate(
                    request.getEmail(),
                    request.getPassword()
            );

            String token = jwtService.generateToken(user);
            return ResponseEntity.ok(
                    new LoginResponse(token, user.getRole().getRoleName(), "login successfully!")
            );

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, null, e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ResisterResponse> register(@RequestBody RegisterRequest request) {
        try {
            userService.register(request.getEmail(), request.getUserName(), request.getPassword(), request.getRole());
            return ResponseEntity
                    .ok(new ResisterResponse(true, "User registered successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ResisterResponse(false, e.getMessage()));
        }
    }

}
