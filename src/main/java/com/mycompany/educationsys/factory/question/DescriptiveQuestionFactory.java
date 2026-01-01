package com.mycompany.educationsys.factory.question;

import com.mycompany.educationsys.dto.QuestionDto;
import com.mycompany.educationsys.entity.question.DescriptiveQuestion;
import com.mycompany.educationsys.entity.question.Question;
import org.springframework.stereotype.Component;

@Component
public class DescriptiveQuestionFactory implements QuestionFactory {
    @Override
    public boolean supports(String questionType) {
        return "DescriptiveQuestion".equalsIgnoreCase(questionType);
    }

    @Override
    public Question create(QuestionDto request) {
        DescriptiveQuestion desc = new DescriptiveQuestion();
        desc.setTitle(request.getTitle());
        desc.setDescription(request.getDescription());
        desc.setStatus(request.getStatus());
        desc.setDifficultyLevel(request.getDifficultyLevel());
        return desc;
    }

    @Override
    public QuestionDto createDto(Question question) {
        System.out.println("create fto ");
        QuestionDto questionDto = new QuestionDto();
        questionDto.setTitle(question.getTitle());
        questionDto.setDescription(question.getDescription());
        questionDto.setStatus(question.getStatus());
        questionDto.setDifficultyLevel(question.getDifficultyLevel());
        questionDto.setQuestionType("DESCRIPTIVE");

        System.out.println("exit dto descriptive " + questionDto.toString());
        return questionDto;
    }
}
