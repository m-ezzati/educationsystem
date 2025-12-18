package com.mycompany.educationsys.services;

import com.mycompany.educationsys.dto.CourseDto;
import com.mycompany.educationsys.entity.Course;
import com.mycompany.educationsys.mapper.CourseMapper;
import com.mycompany.educationsys.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public void addCourse(CourseDto courseDto){
        if(isExistsCourseName(courseDto.getCourseName()) || isExistsCourseCode(courseDto.getCourseCode())){
            throw new RuntimeException("The course code or course name is duplicate!");
        }
        courseRepository.save(courseMapper.toEntity(courseDto));
    }

    public List<Course> findAll(){
        return courseRepository.findAll();
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
