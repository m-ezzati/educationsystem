package com.mycompany.educationsys.services;

import com.mycompany.educationsys.dto.CourseDto;
import com.mycompany.educationsys.dto.UpdateCourseDto;
import com.mycompany.educationsys.entity.Course;
import com.mycompany.educationsys.entity.User;
import com.mycompany.educationsys.entity.enums.CourseStatus;
import com.mycompany.educationsys.exception.CourseNotFoundException;
import com.mycompany.educationsys.exception.UserNotFoundException;
import com.mycompany.educationsys.mapper.CourseMapper;
import com.mycompany.educationsys.repository.CourseRepository;
import com.mycompany.educationsys.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.courseMapper = courseMapper;
    }

    public void addCourse(CourseDto courseDto){
        if(isExistsCourseName(courseDto.getCourseName()) || isExistsCourseCode(courseDto.getCourseCode())){
            throw new RuntimeException("The course code or course name is duplicate!");
        }
        courseRepository.save(courseMapper.toEntity(courseDto));
    }

    public List<Course> findAll(){
        return courseRepository.findByCourseStatus(CourseStatus.ACTIVE);
    }

    @Transactional
    public void updateCourse(Long id, UpdateCourseDto dto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setCourseName(dto.getCourseName());
        course.setStratDate(dto.getStartDate());
        course.setEndDate(dto.getEndDate());

        courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found"));

        course.setCourseStatus(CourseStatus.INACTIVE);
        courseRepository.save(course);

    }

    public void assignProfessor(Long courseId, Long professorId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found"));

        User professor = userRepository.findById(professorId)
                .orElseThrow(() -> new UserNotFoundException("Professor not found"));

        course.setTeacher(professor);
        courseRepository.save(course);
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

}
