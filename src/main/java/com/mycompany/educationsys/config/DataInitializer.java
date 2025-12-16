package com.mycompany.educationsys.config;

import com.mycompany.educationsys.entity.enums.UserStatus;
import com.mycompany.educationsys.repository.RoleRepository;
import com.mycompany.educationsys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mycompany.educationsys.entity.User;
import com.mycompany.educationsys.entity.Role;

import java.util.Set;

@Configuration
@Order(2)
public class DataInitializer {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.email}")
    private String adminEmail;

    @Bean
    public CommandLineRunner createAdmin(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            boolean adminExists = userRepository.findByUsername(adminUsername).isPresent();

            if(adminExists){
                return;
            }

            Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN")
                    .orElseThrow(() -> new IllegalStateException("ROLE_ADMIN not found"));

            User admin = new User();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setUsername(adminUsername);
            admin.setStatus(UserStatus.APPROVED);
            admin.setRoles(Set.of(adminRole));

            userRepository.save(admin);
            System.out.println("Admin user created: username=" + adminUsername + ", password=" + adminPassword);
        };
    }


}
