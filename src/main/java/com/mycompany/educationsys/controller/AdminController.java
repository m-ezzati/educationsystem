    package com.mycompany.educationsys.controller;
    
    import com.mycompany.educationsys.dto.UserDTO;
    import com.mycompany.educationsys.services.UserService;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    
    import java.util.List;
    
    import static java.util.stream.Collectors.toList;

    @RestController
    @RequestMapping("/admin")
    @PreAuthorize("hasRole('Admin')")
    public class AdminController {
    
        private final UserService userService;
    
        public AdminController(UserService userService) {
            this.userService = userService;
        }
    
        @PostMapping("/fetchAllUsers")
        public List<UserDTO> getUser() {
            System.out.println("fetch all users");
            return userService.findAll()
                    .stream()
                    .map(user ->
                            new UserDTO(
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
    
    }
