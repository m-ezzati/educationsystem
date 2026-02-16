package com.mycompany.educationsys.dto;

public class DescriptiveScoreDto {
    private Long studentAnswerId;
    private Integer score;

    public Long getStudentAnswerId() {
        return studentAnswerId;
    }

    public void setStudentAnswerId(Long studentAnswerId) {
        this.studentAnswerId = studentAnswerId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
