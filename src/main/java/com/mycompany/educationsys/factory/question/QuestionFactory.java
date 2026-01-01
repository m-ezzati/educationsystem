package com.mycompany.educationsys.factory.question;

import com.mycompany.educationsys.dto.QuestionDto;
import com.mycompany.educationsys.entity.question.Question;

public interface QuestionFactory {
    boolean supports(String questionType);
    Question create(QuestionDto questionDto);
    QuestionDto createDto(Question question);
}
