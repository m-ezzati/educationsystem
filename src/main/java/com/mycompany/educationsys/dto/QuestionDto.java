package com.mycompany.educationsys.dto;

import com.mycompany.educationsys.entity.question.emuns.DifficultyLevel;
import com.mycompany.educationsys.entity.question.emuns.QuestionStatus;

import java.util.Set;

public class QuestionDto {
    private String title;
    private String description;
    private QuestionStatus status;
    private DifficultyLevel difficultyLevel;

    private Set<String> options;

    private String questionType;

    public QuestionDto() {
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

    public Set<String> getOptions() {
        return options;
    }

    public void setOptions(Set<String> options) {
        this.options = options;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }
}
