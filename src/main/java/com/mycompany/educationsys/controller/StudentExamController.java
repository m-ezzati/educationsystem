package com.mycompany.educationsys.controller;

import com.mycompany.educationsys.dto.QuestionDto;
import com.mycompany.educationsys.security.AuthService;
import com.mycompany.educationsys.services.StudentExamService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student/exams")
public class StudentExamController {
    private final StudentExamService studentExamService;
    private final AuthService authService;

    public StudentExamController(StudentExamService studentExamService,
                                 AuthService authService) {
        this.studentExamService = studentExamService;
        this.authService = authService;
    }

    @PostMapping("/{studentExamId}/start")
    public ResponseEntity<Void> startExam(@PathVariable Long studentExamId,
                                          HttpServletRequest request) {
        Long studentId = authService.getCurrentUserId(request);
        studentExamService.startExam(studentExamId, studentId);
        return ResponseEntity
                .ok().build();
    }

    @PostMapping("/{studentExamId}/finish")
    public ResponseEntity<Void> finishExam(@PathVariable Long studentExamId,
                           HttpServletRequest request) {
        Long studentId = authService.getCurrentUserId(request);
        studentExamService.finishExam(studentExamId, studentId);
        return ResponseEntity
                .ok().build();
    }

    @GetMapping("/{studentExamId}/questions")
    public List<QuestionDto> getExamQuestions(@PathVariable Long studentExamId,
                                              HttpServletRequest request){
        Long studentId = authService.getCurrentUserId(request);
        return studentExamService.getExamQuestions(studentExamId, studentId);
    }

}
