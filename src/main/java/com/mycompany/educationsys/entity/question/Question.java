package com.mycompany.educationsys.entity.question;

import com.mycompany.educationsys.entity.Course;
import com.mycompany.educationsys.entity.QuestionBank;
import com.mycompany.educationsys.entity.base.BaseEntity;
import com.mycompany.educationsys.entity.exam.ExamQuestion;
import com.mycompany.educationsys.entity.question.emuns.DifficultyLevel;
import com.mycompany.educationsys.entity.question.emuns.QuestionStatus;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Question extends BaseEntity {
    private String title;
    private String description;
    private QuestionStatus status;
    private DifficultyLevel difficultyLevel;

    @ManyToOne
    @JoinColumn(name = "question_bank_id")
    private QuestionBank questionBank;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Option> options = new HashSet<>();

    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ExamQuestion> examQuestions = new HashSet<>();

    public Question(){}

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

    public QuestionStatus getStatus() {
        return status;
    }

    public void setStatus(QuestionStatus status) {
        this.status = status;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public QuestionBank getQuestionBank() {
        return questionBank;
    }

    public void setQuestionBank(QuestionBank questionBank) {
        this.questionBank = questionBank;
    }

    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<ExamQuestion> getExamQuestions() {
        return examQuestions;
    }

    public void setExamQuestions(Set<ExamQuestion> examQuestions) {
        this.examQuestions = examQuestions;
    }
}
