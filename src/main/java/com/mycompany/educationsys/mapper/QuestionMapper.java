package com.mycompany.educationsys.mapper;

import com.mycompany.educationsys.dto.CreateQuestionRequest;
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

    public Question toEntity(CreateQuestionRequest request) {
        return factories.stream()
                .filter(f -> f.supports(request.getQuestionType()))
                .findFirst()
                .orElseThrow(InvalidQuestionType::new)
                .create(request);
    }

}
