package com.mycompany.educationsys.entity.exam;

import com.mycompany.educationsys.entity.Course;
import com.mycompany.educationsys.entity.User;
import com.mycompany.educationsys.entity.base.BaseEntity;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Exam extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private String title;
    private String description;
    private Integer duration;

    @Column(nullable = false)
    private Short tryCountAllowed = 1;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private User professor;

    @OneToMany(
            mappedBy = "exam",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ExamQuestion> examQuestions = new HashSet<>();

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

    public Set<ExamQuestion> getExamQuestions() {
        return examQuestions;
    }

    public void setExamQuestions(Set<ExamQuestion> examQuestions) {
        this.examQuestions = examQuestions;
    }

    public Short getTryCountAllowed() {
        return tryCountAllowed;
    }

    public void setTryCountAllowed(Short tryCountAllowed) {
        this.tryCountAllowed = tryCountAllowed;
    }
}
