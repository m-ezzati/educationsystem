package com.mycompany.educationsys.factory.question;

import com.mycompany.educationsys.dto.CreateQuestionRequest;
import com.mycompany.educationsys.entity.question.DescriptiveQuestion;
import com.mycompany.educationsys.entity.question.Question;
import org.springframework.stereotype.Component;

@Component
public class DescriptiveQuestionFactory implements QuestionFactory {
    @Override
    public boolean supports(String questionType) {
        return "DESCRIPTIVE".equalsIgnoreCase(questionType);
    }

    @Override
    public Question create(CreateQuestionRequest request) {
        DescriptiveQuestion desc = new DescriptiveQuestion();
        desc.setTitle(request.getTitle());
        desc.setDescription(request.getDescription());
        desc.setStatus(request.getStatus());
        desc.setDifficultyLevel(request.getDifficultyLevel());
        return desc;
    }
}
