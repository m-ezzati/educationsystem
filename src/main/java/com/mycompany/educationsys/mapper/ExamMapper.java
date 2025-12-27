package com.mycompany.educationsys.mapper;

import com.mycompany.educationsys.dto.ExamDto;
import com.mycompany.educationsys.entity.Course;
import com.mycompany.educationsys.entity.Exam;
import org.springframework.stereotype.Component;
@Component
public class ExamMapper {

    public ExamDto toDto(Exam exam) {
        ExamDto dto = new ExamDto();
        dto.setId(exam.getId());
        dto.setTitle(exam.getTitle());
        dto.setDescription(exam.getDescription());
        dto.setDuration(exam.getDuration());

        if (exam.getCourse() != null) {
            dto.setCourseId(exam.getCourse().getId());
        }

        return dto;
    }

    public Exam toEntity(ExamDto dto, Course course) {
        if (dto == null) return null;

        Exam exam = new Exam();
        exam.setTitle(dto.getTitle());
        exam.setDescription(dto.getDescription());
        exam.setDuration(dto.getDuration());
        exam.setCourse(course);
        return exam;
    }
}