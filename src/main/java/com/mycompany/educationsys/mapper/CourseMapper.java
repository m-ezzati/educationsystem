package com.mycompany.educationsys.mapper;

import com.mycompany.educationsys.dto.CourseDto;
import com.mycompany.educationsys.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
    public CourseDto toDto(Course course) {
        if (course == null) return null;
        CourseDto dto = new CourseDto();
        dto.setId(course.getId());
        dto.setCourseName(course.getCourseName());
        dto.setCourseCode(course.getCourseCode());
        dto.setTeacher(course.getTeacher());
        dto.setStartDate(course.getStratDate());
        dto.setEndDate(course.getEndDate());
        dto.setTeacher(course.getTeacher());
        return dto;
    }

    public Course toEntity(CourseDto dto){
        if (dto == null) return null;
        Course course = new Course();
        course.setId(dto.getId());
        course.setCourseName(dto.getCourseName());
        course.setCourseCode(dto.getCourseCode());
        course.setTeacher(dto.getTeacher());
        course.setStratDate(dto.getStartDate());
        course.setEndDate(dto.getEndDate());

        return course;
    }

}
