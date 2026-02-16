package com.mycompany.educationsys.controller;

import com.mycompany.educationsys.dto.*;
import com.mycompany.educationsys.mapper.CourseMapper;
import com.mycompany.educationsys.security.AuthService;
import com.mycompany.educationsys.services.CourseService;
import com.mycompany.educationsys.services.QuestionService;
import com.mycompany.educationsys.services.impl.CourseServiceImpl;
import com.mycompany.educationsys.services.impl.ExamServiceImpl;
import com.mycompany.educationsys.services.impl.StudentAnswerServiceImpl;
import com.mycompany.educationsys.services.impl.StudentExamServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professor")
@PreAuthorize("hasRole('TEACHER')")
public class ProfessorController {

    private final CourseService courseService;
    private final AuthService authService;
    private final CourseMapper courseMapper;
    private final QuestionService questionService;
    private final ExamServiceImpl examService;
    private final StudentExamServiceImpl studentExamService;
    private final StudentAnswerServiceImpl studentAnswerService;

    public ProfessorController(CourseServiceImpl courseService, AuthService authService, CourseMapper courseMapper, QuestionService questionService, ExamServiceImpl examService, StudentExamServiceImpl studentExamService, StudentAnswerServiceImpl studentAnswerService) {
        this.courseService = courseService;
        this.authService = authService;
        this.courseMapper = courseMapper;
        this.questionService = questionService;
        this.examService = examService;
        this.studentExamService = studentExamService;
        this.studentAnswerService = studentAnswerService;
    }

    @GetMapping("/me/courses")
    public List<CourseDto> getMyCourses(HttpServletRequest request) {
        Long professorId = authService.getCurrentUserId(request);
        return courseService
                .findCoursesByTeacher(professorId)
                .stream()
                .map(courseMapper::toDto)
                .toList();
    }

    @GetMapping("/me/questionBank")
    public List<QuestionDto> getQuestionBank(HttpServletRequest request) {
        Long professorId = authService.getCurrentUserId(request);
        return questionService.findProfessorQuestion(professorId);
    }

    @GetMapping("/course/{courseId}/questionBank")
    public List<QuestionDto> getQuestionBankForCourse(
            HttpServletRequest request,
            @PathVariable Long courseId
    ) {
        Long professorId = authService.getCurrentUserId(request);
        return questionService.findProfessorQuestionForCourse(professorId, courseId);
    }

    @PostMapping("/{examId}/questions")
    public ResponseEntity<Void> assignQuestions(
            @PathVariable Long examId,
            HttpServletRequest request,
            @RequestBody List<AssignQuestionToExamRequest> questions
    ) {

        Long professorId = authService.getCurrentUserId(request);
        examService.assignQuestionsToExam(
                professorId,
                examId,
                questions
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping("/exams/{examId}/results")
    public ResponseEntity<List<StudentExamResultDto>> getExamResults(
            @PathVariable Long examId,
            HttpServletRequest request
    ) {
        List<StudentExamResultDto> results =
                studentExamService.getExamResultsForProfessor(examId);

        return ResponseEntity.ok(results);
    }
    @PostMapping("/answers/descriptive/score")
    public ResponseEntity<Void> scoreDescriptiveAnswer(
            @RequestBody DescriptiveScoreDto dto
    ) {
        studentAnswerService.scoreDescriptiveAnswer(dto);
        return ResponseEntity.ok().build();
    }
}
