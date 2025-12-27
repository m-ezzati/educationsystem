package com.mycompany.educationsys.controller;

import com.mycompany.educationsys.dto.EnrollmentRequest;
import com.mycompany.educationsys.services.EnrollmentService;
import com.mycompany.educationsys.services.impl.EnrollmentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentServiceImpl enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/admin/enrollStudent")
    public ResponseEntity<String> enrollStudent(@RequestBody EnrollmentRequest request) {
        System.out.println("enrollStudent");
        try{
            enrollmentService.enrollStudent(request.getCourseId(), request.getStudentId());
            return ResponseEntity.ok("Student Successfully assigned");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
