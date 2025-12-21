package com.mycompany.educationsys.services.impl;

import com.mycompany.educationsys.entity.Role;
import com.mycompany.educationsys.entity.User;
import com.mycompany.educationsys.entity.enums.UserStatus;
import com.mycompany.educationsys.exception.ForbiddenActionException;
import com.mycompany.educationsys.exception.UserNotFoundException;
import com.mycompany.educationsys.repository.RoleRepository;
import com.mycompany.educationsys.repository.UserRepository;
import com.mycompany.educationsys.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
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

    @Override
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

    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Override
    public void approveUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!isAdmin(user.getRole())) {
            throw new ForbiddenActionException("Only the admin can approve the user.");
        }

        user.setStatus(UserStatus.APPROVED);
        userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void disableUser(Long id) {
        User user = findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (isAdmin(user.getRole())) {
            throw new ForbiddenActionException("Cannot disable admin");
        }

        user.setStatus(UserStatus.DISABLED);
        userRepository.save(user);
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

    private boolean isAdmin(Role role){
        return role.getRoleName().equals("ROLE_ADMIN");
    }
}
