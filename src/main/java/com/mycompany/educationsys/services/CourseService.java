package com.mycompany.educationsys.services;

import com.mycompany.educationsys.dto.CourseDto;
import com.mycompany.educationsys.dto.UpdateCourseDto;
import com.mycompany.educationsys.entity.Course;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CourseService {
    void addCourse(CourseDto courseDto);

    List<Course> findAll();

    @Transactional
    void updateCourse(Long id, UpdateCourseDto dto);

    void deleteCourse(Long id);

    void assignProfessor(Long courseId, Long professorId);
}
