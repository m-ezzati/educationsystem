package com.mycompany.educationsys.services.impl;

import com.mycompany.educationsys.entity.Course;
import com.mycompany.educationsys.entity.Enrollment;
import com.mycompany.educationsys.entity.Role;
import com.mycompany.educationsys.entity.User;
import com.mycompany.educationsys.exception.CourseNotFoundException;
import com.mycompany.educationsys.exception.ForbiddenActionException;
import com.mycompany.educationsys.exception.UserNotFoundException;
import com.mycompany.educationsys.repository.CourseRepository;
import com.mycompany.educationsys.repository.EnrollmentRepository;
import com.mycompany.educationsys.repository.UserRepository;
import com.mycompany.educationsys.services.EnrollmentService;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentServiceImpl(CourseRepository courseRepository, UserRepository userRepository, EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public void enrollStudent(Long courseId, Long studentId) {
        System.out.println("enrollStudent service");
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found"));

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new UserNotFoundException("Student not found"));

        if(!isStudent(student.getRole())){
            throw new ForbiddenActionException("The selected user is not a student.");
        }

        if(enrollmentRepository.existsByStudentAndCourse(student, course)){
            throw new ForbiddenActionException("The user already enrolled in this course.");
        }

        Enrollment enrollment = createEnrollment(course, student);
        enrollmentRepository.save(enrollment);

    }

    private boolean isStudent(Role role){
        return role.getRoleName().equals("ROLE_STUDENT");
    }

    private Enrollment createEnrollment(Course course, User student) {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);

        course.getEnrollments().add(enrollment);
        student.getEnrollments().add(enrollment);

        return enrollment;
    }

}
