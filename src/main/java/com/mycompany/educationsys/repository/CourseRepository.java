package com.mycompany.educationsys.repository;

import com.mycompany.educationsys.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseCode(String courseCode);
    Optional<Object> findByCourseNameIgnoreCase(String courseName);
}
