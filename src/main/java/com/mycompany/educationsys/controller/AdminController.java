    package com.mycompany.educationsys.controller;
    
    import com.mycompany.educationsys.dto.UserDto;
    import com.mycompany.educationsys.exception.ForbiddenActionException;
    import com.mycompany.educationsys.exception.UserNotFoundException;
    import com.mycompany.educationsys.services.impl.UserServiceImpl;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    
    import java.util.List;

    @RestController
    @RequestMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public class AdminController {
    
        private final UserServiceImpl userServiceImpl;
    
        public AdminController(UserServiceImpl userServiceImpl) {
            this.userServiceImpl = userServiceImpl;
        }
    
        @PostMapping("/fetchAllUsers")
        public List<UserDto> getUser() {
            return userServiceImpl.findAll()
                    .stream()
                    .map(user ->
                            new UserDto(
                                    user.getId(),
                                    user.getUsername(),
                                    user.getEmail(),
                                    user.getRole().getRoleName(),
                                    user.getStatus()))
                    .toList();
        }

        @PostMapping("/approve/{userId}")
        public ResponseEntity<?> approveUser(@PathVariable Long userId) {
            userServiceImpl.approveUser(userId);
            return ResponseEntity.ok("User approved successfully");
        }

        @PostMapping("/disableUser/{id}")
        public ResponseEntity<String> disableUser(@PathVariable Long id) {
            try {
                userServiceImpl.disableUser(id);
                return ResponseEntity.ok("User disabled successfully");
            } catch (UserNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (ForbiddenActionException e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            }
        }
    
    }
