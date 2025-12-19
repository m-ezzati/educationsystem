    package com.mycompany.educationsys.controller;
    
    import com.mycompany.educationsys.dto.UserDto;
    import com.mycompany.educationsys.entity.User;
    import com.mycompany.educationsys.exception.ForbiddenActionException;
    import com.mycompany.educationsys.exception.UserNotFoundException;
    import com.mycompany.educationsys.services.UserService;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    
    import java.util.List;
    import java.util.Optional;

    @RestController
    @RequestMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public class AdminController {
    
        private final UserService userService;
    
        public AdminController(UserService userService) {
            this.userService = userService;
        }
    
        @PostMapping("/fetchAllUsers")
        public List<UserDto> getUser() {
            return userService.findAll()
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
            userService.approveUser(userId);
            return ResponseEntity.ok("User approved successfully");
        }

        @PostMapping("/disableUser/{id}")
        public ResponseEntity<String> disableUser(@PathVariable Long id) {
            try {
                userService.disableUser(id);
                return ResponseEntity.ok("User disabled successfully");
            } catch (UserNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (ForbiddenActionException e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            }
        }
    
    }
