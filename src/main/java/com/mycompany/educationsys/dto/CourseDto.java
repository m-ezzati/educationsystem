package com.mycompany.educationsys.dto;

import com.mycompany.educationsys.entity.User;

import java.time.LocalDate;

public class CourseDto {
    private Long id;
    private String courseName;
    private String courseCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private User teacher;

    public CourseDto(){}
    public CourseDto(Long id, String courseName, String courseCode, LocalDate stratDate, LocalDate endDate) {
        this.id = id;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.startDate = stratDate;
        this.endDate = endDate;
    }

    public CourseDto(Long id, String courseName, String courseCode, LocalDate stratDate, LocalDate endDate, User teacher) {
        this.id = id;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.startDate = stratDate;
        this.endDate = endDate;
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }
}
