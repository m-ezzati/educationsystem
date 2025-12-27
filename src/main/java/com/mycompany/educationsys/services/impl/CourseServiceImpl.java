package com.mycompany.educationsys.services.impl;

import com.mycompany.educationsys.dto.CourseDto;
import com.mycompany.educationsys.dto.ExamDto;
import com.mycompany.educationsys.dto.UpdateCourseDto;
import com.mycompany.educationsys.entity.Course;
import com.mycompany.educationsys.entity.Role;
import com.mycompany.educationsys.entity.User;
import com.mycompany.educationsys.entity.enums.CourseStatus;
import com.mycompany.educationsys.exception.CourseNotFoundException;
import com.mycompany.educationsys.exception.ForbiddenActionException;
import com.mycompany.educationsys.exception.UserNotFoundException;
import com.mycompany.educationsys.mapper.CourseMapper;
import com.mycompany.educationsys.mapper.ExamMapper;
import com.mycompany.educationsys.repository.CourseRepository;
import com.mycompany.educationsys.repository.ExamRepository;
import com.mycompany.educationsys.repository.UserRepository;
import com.mycompany.educationsys.services.CourseService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseMapper courseMapper;
    private final ExamRepository examRepository;
    private final ExamMapper examMapper;

    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository, CourseMapper courseMapper, ExamRepository examRepository, ExamMapper examMapper) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.courseMapper = courseMapper;
        this.examRepository = examRepository;
        this.examMapper = examMapper;
    }

    @Override
    public void addCourse(CourseDto courseDto){
        if(isExistsCourseName(courseDto.getCourseName()) || isExistsCourseCode(courseDto.getCourseCode())){
            throw new RuntimeException("The course code or course name is duplicate!");
        }
        courseRepository.save(courseMapper.toEntity(courseDto));
    }

    @Override
    public List<CourseDto> findAll(){
        return courseRepository.findByCourseStatus(CourseStatus.ACTIVE)
                .stream()
                .map(courseMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public void updateCourse(Long id, UpdateCourseDto dto) {
        Course course = getCourseById(id);

        course.setCourseName(dto.getCourseName());
        course.setStratDate(dto.getStartDate());
        course.setEndDate(dto.getEndDate());

        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = getCourseById(id);

        course.setCourseStatus(CourseStatus.INACTIVE);
        courseRepository.save(course);

    }

    @Override
    public void assignProfessor(Long courseId, Long professorId) {
        Course course = getCourseById(courseId);

        User professor = userRepository.findById(professorId)
                .orElseThrow(() -> new UserNotFoundException("Professor not found"));

        if(!isProfessor(professor.getRole())){
            throw new ForbiddenActionException("The selected user is not a teacher.");
        }
        course.setTeacher(professor);
        courseRepository.save(course);
    }

    @Override
    public List<Course> findCoursesByTeacher(Long professorId) {
        User professor = userRepository.findById(professorId)
                .orElseThrow(() -> new UserNotFoundException("Professor not found"));

        if(!isProfessor(professor.getRole())){
            throw new ForbiddenActionException("The selected user is not a teacher.");
        }

        return courseRepository.findCoursesByTeacher(professor);
    }

    @Override
    public List<ExamDto> findExamsByCourse(Long courseId){
        return examRepository.findByCourseId(courseId)
                .stream()
                .map(examMapper::toDto)
                .toList();
    }

    @Override
    public List<ExamDto> findExamsByCourseAndProfessor(Long courseId, Long professorId){
        return examRepository.findByCourseIdAndProfessorId(courseId, professorId)
                .stream()
                .map(examMapper::toDto)
                .toList();
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
    private Course getCourseById(Long courseId){
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found"));
    }
}
