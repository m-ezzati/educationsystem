package com.mycompany.educationsys.controller;

import com.mycompany.educationsys.dto.CourseDto;
import com.mycompany.educationsys.dto.ResisterResponse;
import com.mycompany.educationsys.mapper.CourseMapper;
import com.mycompany.educationsys.services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {

    private final CourseService courseService;
    private final CourseMapper courseMapper;

    public CourseController(CourseService courseService, CourseMapper courseMapper) {
        this.courseService = courseService;
        this.courseMapper = courseMapper;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/addCourse")
    public ResponseEntity<?> addCourse(@RequestBody CourseDto courseDto){
        System.out.println("addCourse " + courseDto);
        try {
            courseService.addCourse(courseDto);
            return ResponseEntity
                    .ok("Course successfully added");
        }catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("message " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/fetchAllCourse")
    public List<CourseDto> fetchAllCourse(){
        System.out.println("fetch all course ");
        return courseService.findAll()
                .stream()
                .map(courseMapper::toDto)
                .toList();
    }
}
