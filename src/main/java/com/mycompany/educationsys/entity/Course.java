package com.mycompany.educationsys.entity;

import com.mycompany.educationsys.entity.base.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
public class Course extends BaseEntity {
    private String courseName;
    private String courseIdentity;
    private LocalDate stratDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @ManyToMany
    @JoinTable(
            name="course_students",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<User> students;

    public Course() {
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseIdentity() {
        return courseIdentity;
    }

    public void setCourseIdentity(String courseIdentity) {
        this.courseIdentity = courseIdentity;
    }

    public LocalDate getStratDate() {
        return stratDate;
    }

    public void setStratDate(LocalDate stratDate) {
        this.stratDate = stratDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public Set<User> getStudents() {
        return students;
    }

    public void setStudents(Set<User> students) {
        this.students = students;
    }
}
