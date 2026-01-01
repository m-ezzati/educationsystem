package com.mycompany.educationsys.controller;

import com.mycompany.educationsys.dto.QuestionDto;
import com.mycompany.educationsys.security.AuthService;
import com.mycompany.educationsys.services.QuestionService;
import com.mycompany.educationsys.services.impl.QuestionServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/add/{courseId}")
    public ResponseEntity<?> addQuestion(
            HttpServletRequest request,
            @PathVariable Long courseId,
            @RequestBody QuestionDto questionDto)
    {
        Long professorId = authService.getCurrentUserId(request);
        questionService.addQuestion(professorId, courseId, questionDto);

        return ResponseEntity
                .ok("Question successfully added");
    }
}
