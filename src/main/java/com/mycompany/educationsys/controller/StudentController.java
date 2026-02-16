package com.mycompany.educationsys.controller;

import com.mycompany.educationsys.dto.CourseDto;
import com.mycompany.educationsys.dto.ExamDto;
import com.mycompany.educationsys.entity.User;
import com.mycompany.educationsys.mapper.CourseMapper;
import com.mycompany.educationsys.mapper.ExamMapper;
import com.mycompany.educationsys.security.AuthService;
import com.mycompany.educationsys.services.ExamService;
import com.mycompany.educationsys.services.impl.CourseServiceImpl;
import com.mycompany.educationsys.services.impl.EnrollmentServiceImpl;
import com.mycompany.educationsys.services.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
//@PreAuthorize("hasRole('STUDENT')")
public class StudentController {
    private final AuthService authService;
    private final CourseServiceImpl courseService;
    private final CourseMapper courseMapper;
    private final ExamService examService;
    private final ExamMapper examMapper;

    public StudentController(AuthService authService, CourseServiceImpl courseService, CourseMapper courseMapper, ExamService examService, ExamMapper examMapper) {
        this.authService = authService;
        this.courseService = courseService;
        this.courseMapper = courseMapper;
        this.examService = examService;
        this.examMapper = examMapper;
    }

    @GetMapping("/me/courses")
    public List<CourseDto> getMyCourses(HttpServletRequest request) {
        Long studentId = authService.getCurrentUserId(request);
        return courseService
                .findCoursesByStudent(studentId)
                .stream()
                .map(courseMapper::toDto)
                .toList();
    }

    @GetMapping("/me/{courseId}/exams")
    public List<ExamDto> getMyExam(
            HttpServletRequest request,
            @PathVariable Long courseId) {
        Long studentId = authService.getCurrentUserId(request);
        return examService.findExamByCourseAndStudent(studentId, courseId)
                .stream()
                .map(examMapper::toDto)
                .toList();
    }
}
