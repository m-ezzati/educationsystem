package com.mycompany.educationsys.mapper;

import com.mycompany.educationsys.dto.QuestionDto;
import com.mycompany.educationsys.entity.question.Question;
import com.mycompany.educationsys.exception.question.InvalidQuestionType;
import com.mycompany.educationsys.factory.question.QuestionFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionMapper {
    private final List<QuestionFactory> factories;

    public QuestionMapper(List<QuestionFactory> factories) {
        this.factories = factories;
    }

    public Question toEntity(QuestionDto dto) {
        return factories.stream()
                .filter(f -> f.supports(dto.getQuestionType()))
                .findFirst()
                .orElseThrow(InvalidQuestionType::new)
                .create(dto);
    }

    public QuestionDto toDto(Question question){
        System.out.println("to dto" + factories.stream()
                .filter(f -> f.supports(question.getClass().getSimpleName())).findFirst());

        System.out.println("befor err");
        return factories.stream()
                .filter(f -> f.supports(question.getClass().getSimpleName()))
                .findFirst()
                .orElseThrow(InvalidQuestionType::new)
                .createDto(question);
    }

}
