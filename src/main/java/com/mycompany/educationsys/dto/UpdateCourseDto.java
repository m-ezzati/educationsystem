package com.mycompany.educationsys.dto;

import java.time.LocalDate;

public class UpdateCourseDto {
    private Long id;
    private String courseName;
    private LocalDate startDate;
    private LocalDate endDate;

    public Long getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
