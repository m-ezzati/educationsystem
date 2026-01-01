package com.mycompany.educationsys.entity;

import com.mycompany.educationsys.entity.base.BaseEntity;
import com.mycompany.educationsys.entity.question.Question;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class QuestionBank extends BaseEntity {

    @OneToMany(mappedBy = "questionBank", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    @OneToOne
    private Course course;

    public QuestionBank() {}

    @OneToMany(mappedBy = "questionBank", cascade = CascadeType.ALL, orphanRemoval = true)

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question question) {
        getQuestions().add(question);
    }


    public Optional<Course> getCourse() {
        return Optional.of(course);
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
