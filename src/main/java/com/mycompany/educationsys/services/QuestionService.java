package com.mycompany.educationsys.services;

import com.mycompany.educationsys.dto.CreateQuestionRequest;

public interface QuestionService {
    void addQuestion(Long professorId, CreateQuestionRequest questionRequest);
}
