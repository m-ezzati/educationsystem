package com.mycompany.educationsys.controller;

import com.mycompany.educationsys.dto.CourseDto;
import com.mycompany.educationsys.mapper.CourseMapper;
import com.mycompany.educationsys.security.AuthService;
import com.mycompany.educationsys.services.CourseService;
import com.mycompany.educationsys.services.impl.CourseServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@PreAuthorize("hasRole('TEACHER')")
public class ProfessorController {

    private final CourseService courseService;
    private final AuthService authService;
    private final CourseMapper courseMapper;

    public ProfessorController(CourseServiceImpl courseService, AuthService authService, CourseMapper courseMapper) {
        this.courseService = courseService;
        this.authService = authService;
        this.courseMapper = courseMapper;
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
}
