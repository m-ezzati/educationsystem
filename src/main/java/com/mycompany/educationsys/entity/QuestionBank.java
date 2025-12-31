package com.mycompany.educationsys.entity;

import com.mycompany.educationsys.entity.base.BaseEntity;
import com.mycompany.educationsys.entity.question.Question;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class QuestionBank extends BaseEntity {

    private List<Question> questions;

    public QuestionBank() {}
}
