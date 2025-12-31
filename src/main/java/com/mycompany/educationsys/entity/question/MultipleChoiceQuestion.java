package com.mycompany.educationsys.entity.question;

import jakarta.persistence.Entity;

import java.util.Set;

@Entity
public class MultipleChoiceQuestion extends Question{

    private Set<Option> options;

    public MultipleChoiceQuestion(){}
}
