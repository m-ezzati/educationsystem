package com.mycompany.educationsys.dto;

public class AssignQuestionToExamRequest {
    private Long questionId;
    private Integer score;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
