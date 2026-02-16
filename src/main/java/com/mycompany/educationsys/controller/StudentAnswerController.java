package com.mycompany.educationsys.controller;

import com.mycompany.educationsys.dto.StudentAnswerRegister;
import com.mycompany.educationsys.security.AuthService;
import com.mycompany.educationsys.services.StudentAnswerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/studentExam")
public class StudentAnswerController {
    private final AuthService authService;
    private final StudentAnswerService studentAnswerService;

    public StudentAnswerController(AuthService authService, StudentAnswerService studentAnswerService) {
        this.authService = authService;
        this.studentAnswerService = studentAnswerService;
    }

    @PostMapping("/answer")
    public ResponseEntity<?> answer(
            @RequestBody StudentAnswerRegister studentAnswerRegister,
            HttpServletRequest request
    ){
        Long studentId = authService.getCurrentUserId(request);
        studentAnswerService.studentAnswer(studentAnswerRegister, studentId);
        return ResponseEntity
                .ok("The answer successfully registered.");
    }
}
