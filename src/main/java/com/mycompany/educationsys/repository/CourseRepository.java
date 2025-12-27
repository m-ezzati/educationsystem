package com.mycompany.educationsys.repository;

import com.mycompany.educationsys.entity.Course;
import com.mycompany.educationsys.entity.User;
import com.mycompany.educationsys.entity.enums.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseCode(String courseCode);
//    Optional<Course> findById(Long courseId);
    Optional<Object> findByCourseNameIgnoreCase(String courseName);
    List<Course> findByCourseStatus(CourseStatus courseStatus);
    List<Course> findCoursesByTeacher(User professor);

}
