package com.mycompany.educationsys.entity.question;

import com.mycompany.educationsys.entity.base.BaseEntity;
import com.mycompany.educationsys.entity.question.emuns.DifficultyLevel;
import com.mycompany.educationsys.entity.question.emuns.QuestionStatus;
import jakarta.persistence.Entity;

@Entity
public abstract class Question extends BaseEntity {
    private String title;
    private String description;
    private QuestionStatus status;
    private DifficultyLevel difficultyLevel;

    public Question(){}
}
