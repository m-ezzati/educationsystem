package com.mycompany.educationsys.mapper;

import com.mycompany.educationsys.dto.CourseDto;
import com.mycompany.educationsys.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
    public CourseDto toDto(Course course) {
        System.out.println("mapper");
        if (course == null) return null;
        CourseDto dto = new CourseDto();
        dto.setId(course.getId());
        dto.setCourseName(course.getCourseName());
        dto.setCourseCode(course.getCourseCode());
        dto.setStartDate(course.getStratDate());
        dto.setEndDate(course.getEndDate());
        System.out.println("mapper + " + dto);
        return dto;
    }

    public Course toEntity(CourseDto dto){
        if (dto == null) return null;
        Course course = new Course();
        course.setId(dto.getId());
        course.setCourseName(dto.getCourseName());
        course.setCourseCode(dto.getCourseCode());
        course.setStratDate(dto.getStartDate());
        course.setEndDate(dto.getEndDate());

        return course;
    }

}
