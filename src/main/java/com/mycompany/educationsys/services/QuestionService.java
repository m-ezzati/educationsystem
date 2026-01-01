package com.mycompany.educationsys.services;

import com.mycompany.educationsys.dto.QuestionDto;

import java.util.List;

public interface QuestionService {
    void addQuestion(Long professorId, Long courseId, QuestionDto questionRequest);
    List<QuestionDto> findProfessorQuestion(Long professorId);
    List<QuestionDto> findProfessorQuestionForCourse(Long professorId, Long courseId);
}
