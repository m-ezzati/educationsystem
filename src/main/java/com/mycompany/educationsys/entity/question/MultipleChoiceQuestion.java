package com.mycompany.educationsys.entity.question;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class MultipleChoiceQuestion extends Question {

    @OneToOne
    @JoinColumn(name = "correct_option_id")
    private Option correctOption;

    public Option getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(Option correctOption) {
        this.correctOption = correctOption;
    }

    public MultipleChoiceQuestion() {
    }
}
