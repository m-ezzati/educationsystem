package com.mycompany.educationsys.entity.exam;

import com.mycompany.educationsys.entity.User;
import com.mycompany.educationsys.entity.base.BaseEntity;
import com.mycompany.educationsys.entity.exam.enums.ExamStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class StudentExam extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    private Exam exam;

    @OneToMany(mappedBy = "studentExam", cascade = CascadeType.ALL)
    private List<StudentAnswer> answers;

    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;

    private Integer score;
    private Integer totalScore;

    @Column(nullable = false)
    private Short tryCount= 0;

    @Enumerated(EnumType.STRING)
    private ExamStatus status;


    public StudentExam() {
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public List<StudentAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<StudentAnswer> answers) {
        this.answers = answers;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Short getTryCount() {
        return tryCount;
    }

    public void setTryCount(Short tryCount) {
        this.tryCount = tryCount;
    }

    public ExamStatus getStatus() {
        return status;
    }

    public void setStatus(ExamStatus status) {
        this.status = status;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }
}
