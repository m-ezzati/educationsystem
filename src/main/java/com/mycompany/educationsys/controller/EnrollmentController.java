package com.mycompany.educationsys.controller;

import com.mycompany.educationsys.services.EnrollmentService;
import com.mycompany.educationsys.services.impl.EnrollmentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentServiceImpl enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PreAuthorize(("hasRole('ADMIN')"))
    @PostMapping("/admin/enrollStudent/{courseId}/{studentId}")
    public ResponseEntity<?> assignStudent(
            @PathVariable Long courseId,
            @PathVariable Long studentId
    ){
        System.out.println("enrollStudent");
        try{
            enrollmentService.enrollStudent(courseId, studentId);
            return ResponseEntity.ok("Student Successfully assigned");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
