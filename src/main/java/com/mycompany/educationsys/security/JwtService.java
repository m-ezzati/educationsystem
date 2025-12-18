package com.mycompany.educationsys.security;

import com.mycompany.educationsys.entity.Role;
import com.mycompany.educationsys.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import java.util.Date;

import java.util.stream.Collectors;

@Service
public class JwtService {
    private static final String SECRET_KEY = "mySecretKey12345";

    private static final long EXPIRATION = 1000 * 60 * 60;

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole().getRoleName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Claims validateToken(String token) {
        System.out.println("validate token" + token);
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
