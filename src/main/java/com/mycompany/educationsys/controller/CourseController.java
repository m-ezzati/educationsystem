package com.mycompany.educationsys.controller;

import com.mycompany.educationsys.dto.CourseDto;
import com.mycompany.educationsys.dto.CreateExamRequest;
import com.mycompany.educationsys.dto.ExamDto;
import com.mycompany.educationsys.dto.UpdateCourseDto;
import com.mycompany.educationsys.mapper.CourseMapper;
import com.mycompany.educationsys.repository.ExamRepository;
import com.mycompany.educationsys.security.AuthService;
import com.mycompany.educationsys.services.ExamService;
import com.mycompany.educationsys.services.impl.CourseServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {

    private final CourseServiceImpl courseService;
    private final AuthService authService;
    private final ExamService examService;

    public CourseController(CourseServiceImpl courseService, AuthService authService, ExamService examService) {
        this.courseService = courseService;
        this.authService = authService;
        this.examService = examService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/addCourse")
    public ResponseEntity<?> addCourse(@RequestBody CourseDto courseDto) {
        courseService.addCourse(courseDto);
        return ResponseEntity
                .ok("Course successfully added");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/deleteCourse/{id}")
    public ResponseEntity<?> disableCourse(@PathVariable Long id) {
        System.out.println("delete course ");
        courseService.deleteCourse(id);
        return ResponseEntity
                .ok("Course successfully deleted");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/updateCourse/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody UpdateCourseDto dto) {
        courseService.updateCourse(id, dto);
        return ResponseEntity.ok("Course updated successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/assignProfessor/{courseId}/{professorId}")
    public ResponseEntity<?> assignProfessor(
            @PathVariable Long professorId,
            @PathVariable Long courseId) {

        courseService.assignProfessor(courseId, professorId);
        return ResponseEntity.ok("Professor Successfully assigned");

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/fetchAllCourse")
    public List<CourseDto> fetchAllCourse() {
        return courseService.findAll();
    }

    @PostMapping("/courses/{courseId}/addExam")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> addExam(
            @PathVariable Long courseId,
            @RequestBody CreateExamRequest createExamRequest,
            HttpServletRequest request) {
        Long professorId = authService.getCurrentUserId(request);
        System.out.println("controller");
        examService.addExam(professorId, courseId, createExamRequest);
        return ResponseEntity.ok("Exam Successfully added");
    }

    @PutMapping("/courses/{examId}/editExam")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> editExam(
            @PathVariable Long examId,
            @RequestBody CreateExamRequest createExamRequest,
            HttpServletRequest request) {
        Long professorId = authService.getCurrentUserId(request);
        examService.editExam(professorId, examId, createExamRequest);
        return ResponseEntity.ok("Exam Successfully edited");
    }

    @DeleteMapping("/courses/{examId}/deleteExam")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> deleteExam(
            @PathVariable Long examId,
            HttpServletRequest request) {
        Long professorId = authService.getCurrentUserId(request);
        examService.deleteExam(examId, professorId);
        return ResponseEntity.ok("Exam Successfully deleted");
    }

    @GetMapping("/courses/{courseId}/getExams")
    @PreAuthorize("hasRole('TEACHER')")
    public List<ExamDto> findExamsByCourse(@PathVariable Long courseId) {
        return courseService.findExamsByCourse(courseId);
    }

    @GetMapping("/courses/{courseId}/getProfessorExams")
    @PreAuthorize("hasRole('TEACHER')")
    public List<ExamDto> findExamsByCourseForProfessor(
            @PathVariable Long courseId,
            HttpServletRequest request) {
        Long professorId = authService.getCurrentUserId(request);
        return courseService.findExamsByCourseAndProfessor(courseId, professorId);
    }

}
