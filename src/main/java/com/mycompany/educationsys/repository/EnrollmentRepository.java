package com.mycompany.educationsys.repository;

import com.mycompany.educationsys.entity.Course;
import com.mycompany.educationsys.entity.Enrollment;
import com.mycompany.educationsys.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByStudentAndCourse(User student, Course course);
}
