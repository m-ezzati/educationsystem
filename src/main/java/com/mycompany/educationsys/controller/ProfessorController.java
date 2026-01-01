package com.mycompany.educationsys.controller;

import com.mycompany.educationsys.dto.CourseDto;
import com.mycompany.educationsys.dto.QuestionDto;
import com.mycompany.educationsys.mapper.CourseMapper;
import com.mycompany.educationsys.security.AuthService;
import com.mycompany.educationsys.services.CourseService;
import com.mycompany.educationsys.services.QuestionService;
import com.mycompany.educationsys.services.impl.CourseServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/professor")
@PreAuthorize("hasRole('TEACHER')")
public class ProfessorController {

    private final CourseService courseService;
    private final AuthService authService;
    private final CourseMapper courseMapper;
    private final QuestionService questionService;

    public ProfessorController(CourseServiceImpl courseService, AuthService authService, CourseMapper courseMapper, QuestionService questionService) {
        this.courseService = courseService;
        this.authService = authService;
        this.courseMapper = courseMapper;
        this.questionService = questionService;
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

    @GetMapping("course/{courseId}/questionBank")
    public List<QuestionDto> getQuestionBankForCourse(
            HttpServletRequest request,
            @PathVariable Long courseId
    ) {
        Long professorId = authService.getCurrentUserId(request);
        return questionService.findProfessorQuestionForCourse(professorId, courseId);
    }
}
