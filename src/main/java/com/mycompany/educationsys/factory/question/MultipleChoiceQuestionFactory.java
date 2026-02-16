package com.mycompany.educationsys.factory.question;

import com.mycompany.educationsys.dto.QuestionDto;
import com.mycompany.educationsys.entity.question.MultipleChoiceQuestion;
import com.mycompany.educationsys.entity.question.Option;
import com.mycompany.educationsys.entity.question.Question;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class MultipleChoiceQuestionFactory implements QuestionFactory{
    @Override
    public boolean supports(String questionType) {
        return "MultipleChoiceQuestion".equalsIgnoreCase(questionType);
    }


    @Override
    public Question create(QuestionDto dto) {
        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion();
        mcq.setTitle(dto.getTitle());
        mcq.setDescription(dto.getDescription());
        mcq.setStatus(dto.getStatus());
        mcq.setDifficultyLevel(dto.getDifficultyLevel());

        if (dto.getOptions() != null && !dto.getOptions().isEmpty()) {
            Set<Option> options = new HashSet<>();
            dto.getOptions().forEach(optText -> {
                Option option = new Option(optText);
                option.setQuestion(mcq);
                options.add(option);
            });
            mcq.setOptions(options);
        }
        return mcq;
    }

    @Override
    public QuestionDto createDto(Question question) {
        System.out.println("create dto multi " + question.toString() );
        QuestionDto questionDto = new QuestionDto();
        questionDto.setTitle(question.getTitle());
        questionDto.setDescription(question.getDescription());
        questionDto.setStatus(question.getStatus());
        questionDto.setDifficultyLevel(question.getDifficultyLevel());
        questionDto.setQuestionType("MULTIPLE_CHOICE");

        if(question.getOptions() != null && !question.getOptions().isEmpty()){
            Set<String> options = new HashSet<>();
            question.getOptions()
                    .forEach(option -> {
                        options.add(option.getText());
                    });
            questionDto.setOptions(options);
        }

        return questionDto;
    }
}
