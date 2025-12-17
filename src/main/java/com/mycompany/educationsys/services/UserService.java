package com.mycompany.educationsys.services;

import com.mycompany.educationsys.dto.RegisterRequest;
import com.mycompany.educationsys.entity.Role;
import com.mycompany.educationsys.entity.User;
import com.mycompany.educationsys.entity.enums.UserStatus;
import com.mycompany.educationsys.repository.RoleRepository;
import com.mycompany.educationsys.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        if (user.getStatus() != UserStatus.APPROVED) {
            throw new RuntimeException("User account is not approved yet");
        }
        return user;
    }

    public void register(String email, String username, String password, String roleName) {

        if (isEmailExist(email)) {
            throw new RuntimeException("This email is already registered!");
        }
        if (isUsernameExist(username)) {
            throw new RuntimeException("The username is already exists!");
        }

        Optional<Role> role = fetchRole(roleName);
        if (role.isEmpty()) {
            throw new RuntimeException("Invalid role!");
        }

        User user = new User(username, email, passwordEncoder.encode(password), UserStatus.PENDING, role.get());
        userRepository.save(user);
    }

    public List<User> findAll(){
        System.out.println("find all service");
        return userRepository.findAll();
    }

    private boolean isUsernameExist(String username) {
        return userRepository
                .findByUsername(username)
                .isPresent();
    }

    private boolean isEmailExist(String email) {
        return userRepository
                .findByEmail(email)
                .isPresent();
    }

    private Optional<Role> fetchRole(String role) {
        return roleRepository.findByRoleName("ROLE_" + role);
    }

}
