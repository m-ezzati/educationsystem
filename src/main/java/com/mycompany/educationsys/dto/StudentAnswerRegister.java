package com.mycompany.educationsys.dto;

public class StudentAnswerRegister {

    private String answer;
    private Long selectedOptionId;
    private Long studentExamId;
    private Long questionId;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Long getSelectedOptionId() {
        return selectedOptionId;
    }

    public void setSelectedOptionId(Long selectedOptionId) {
        this.selectedOptionId = selectedOptionId;
    }

    public Long getStudentExamId() {
        return studentExamId;
    }

    public void setStudentExamId(Long studentExamId) {
        this.studentExamId = studentExamId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }


    @Override
    public String toString() {
        return "StudentAnswerRegister{" +
                "answer='" + answer + '\'' +
                ", studentExamId=" + studentExamId +
                ", questionId=" + questionId +
                '}';
    }
}



