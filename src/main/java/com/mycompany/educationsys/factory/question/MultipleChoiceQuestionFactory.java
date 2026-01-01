package com.mycompany.educationsys.factory.question;

import com.mycompany.educationsys.dto.CreateQuestionRequest;
import com.mycompany.educationsys.entity.question.MultipleChoiceQuestion;
import com.mycompany.educationsys.entity.question.Option;
import com.mycompany.educationsys.entity.question.Question;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MultipleChoiceQuestionFactory implements QuestionFactory{
    @Override
    public boolean supports(String questionType) {
        return "MULTIPLE_CHOICE".equalsIgnoreCase(questionType);
    }


    @Override
    public Question create(CreateQuestionRequest request) {
        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion();
        mcq.setTitle(request.getTitle());
        mcq.setDescription(request.getDescription());
        mcq.setStatus(request.getStatus());
        mcq.setDifficultyLevel(request.getDifficultyLevel());

        if (request.getOptions() != null && !request.getOptions().isEmpty()) {
            Set<Option> options = new HashSet<>();
            request.getOptions().forEach(optText -> {
                Option option = new Option(optText);
                option.setQuestion(mcq);
                options.add(option);
            });
            mcq.setOptions(options);
        }
        return mcq;
    }
}
