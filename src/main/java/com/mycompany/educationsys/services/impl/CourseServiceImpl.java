package com.mycompany.educationsys.services.impl;

import com.mycompany.educationsys.dto.CourseDto;
import com.mycompany.educationsys.dto.UpdateCourseDto;
import com.mycompany.educationsys.entity.Course;
import com.mycompany.educationsys.entity.Enrollment;
import com.mycompany.educationsys.entity.Role;
import com.mycompany.educationsys.entity.User;
import com.mycompany.educationsys.entity.enums.CourseStatus;
import com.mycompany.educationsys.exception.CourseNotFoundException;
import com.mycompany.educationsys.exception.ForbiddenActionException;
import com.mycompany.educationsys.exception.UserNotFoundException;
import com.mycompany.educationsys.mapper.CourseMapper;
import com.mycompany.educationsys.repository.CourseRepository;
import com.mycompany.educationsys.repository.EnrollmentRepository;
import com.mycompany.educationsys.repository.UserRepository;
import com.mycompany.educationsys.services.CourseService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseMapper courseMapper;

    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository, EnrollmentRepository enrollmentRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.courseMapper = courseMapper;
    }

    @Override
    public void addCourse(CourseDto courseDto){
        if(isExistsCourseName(courseDto.getCourseName()) || isExistsCourseCode(courseDto.getCourseCode())){
            throw new RuntimeException("The course code or course name is duplicate!");
        }
        courseRepository.save(courseMapper.toEntity(courseDto));
    }

    @Override
    public List<Course> findAll(){
        return courseRepository.findByCourseStatus(CourseStatus.ACTIVE);
    }

    @Transactional
    @Override
    public void updateCourse(Long id, UpdateCourseDto dto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setCourseName(dto.getCourseName());
        course.setStratDate(dto.getStartDate());
        course.setEndDate(dto.getEndDate());

        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found"));

        course.setCourseStatus(CourseStatus.INACTIVE);
        courseRepository.save(course);

    }

    @Override
    public void assignProfessor(Long courseId, Long professorId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found"));

        User professor = userRepository.findById(professorId)
                .orElseThrow(() -> new UserNotFoundException("Professor not found"));

        if(!isProfessor(professor.getRole())){
            throw new ForbiddenActionException("The selected user is not a teacher.");
        }
        course.setTeacher(professor);
        courseRepository.save(course);
    }

    @Override
    public void assignStudent(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found"));

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new UserNotFoundException("Student not found"));

        if(!isStudent(student.getRole())){
            throw new ForbiddenActionException("The selected user is not a student.");
        }

        Enrollment enrollment = createEnrollment(course, student);
        enrollmentRepository.save(enrollment);

    }

    private boolean isExistsCourseCode(String courseCode){
        return courseRepository
                .findByCourseCode(courseCode)
                .isPresent();
    }
    private boolean isExistsCourseName(String courseName){
        return courseRepository
                .findByCourseNameIgnoreCase(courseName)
                .isPresent();
    }
    private boolean isProfessor(Role role){
        return role.getRoleName().equals("ROLE_TEACHER");
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
    }}
