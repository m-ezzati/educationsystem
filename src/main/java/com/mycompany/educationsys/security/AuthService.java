package com.mycompany.educationsys.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtService jwtService;

    public AuthService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public Long getCurrentUserId(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        Claims claims = jwtService.validateToken(token);
        return claims.get("id", Long.class);
    }
}
