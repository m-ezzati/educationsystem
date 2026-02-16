package com.mycompany.educationsys.entity.exam;

import com.mycompany.educationsys.entity.base.BaseEntity;
import com.mycompany.educationsys.entity.question.Option;
import com.mycompany.educationsys.entity.question.Question;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Entity
public class StudentAnswer extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private StudentExam studentExam;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @Column(columnDefinition = "TEXT")
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Option selectedOption;

    private Integer earnedScore;

    public StudentExam getStudentExam() {
        return studentExam;
    }

    public void setStudentExam(StudentExam studentExam) {
        this.studentExam = studentExam;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Option getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(Option choosenOption) {
        this.selectedOption = choosenOption;
    }

    public Integer getEarnedScore() {
        return earnedScore;
    }

    public void setEarnedScore(Integer earnedScore) {
        this.earnedScore = earnedScore;
    }
}
