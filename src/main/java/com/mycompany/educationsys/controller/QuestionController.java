package com.mycompany.educationsys.controller;

import com.mycompany.educationsys.dto.CreateQuestionRequest;
import com.mycompany.educationsys.entity.User;
import com.mycompany.educationsys.security.AuthService;
import com.mycompany.educationsys.services.QuestionService;
import com.mycompany.educationsys.services.impl.QuestionServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final AuthService authService;

    public QuestionController(QuestionServiceImpl questionService, AuthService authService) {
        this.questionService = questionService;
        this.authService = authService;
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/add")
    public ResponseEntity<?> addQuestion(
            HttpServletRequest request,
            @RequestBody CreateQuestionRequest questionDto)
    {
        Long professorId = authService.getCurrentUserId(request);
        questionService.addQuestion(professorId, questionDto);

        return ResponseEntity
                .ok("Question successfully added");
    }
}
