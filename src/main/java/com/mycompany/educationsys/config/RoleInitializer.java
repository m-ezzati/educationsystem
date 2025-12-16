package com.mycompany.educationsys.config;


import com.mycompany.educationsys.entity.Role;
import com.mycompany.educationsys.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RoleInitializer implements CommandLineRunner{

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        createIfNotExists("ROLE_ADMIN");
        createIfNotExists("ROLE_TEACHER");
        createIfNotExists("ROLE_STUDENT");
    }

    private void createIfNotExists(String roleName) {
        if (!roleRepository.existsByRoleName(roleName)) {
            roleRepository.save(new Role(roleName));
        }
    }

}