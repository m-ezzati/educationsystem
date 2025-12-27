package com.mycompany.educationsys.entity;

import com.mycompany.educationsys.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Exam extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private String title;
    private String description;
    private Integer duration; // min

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private User professor;

    public Exam(){}

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public User getProfessor() {
        return professor;
    }

    public void setProfessor(User professor) {
        this.professor = professor;
    }
}
