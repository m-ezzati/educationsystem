package com.mycompany.educationsys.services;

import com.mycompany.educationsys.dto.CreateExamRequest;

public interface ExamService {
    void addExam(Long professorId, Long courseId,
                 CreateExamRequest createExamRequest);
    void deleteExam(Long examId, Long professorId);

    void editExam(Long professorId, Long examId, CreateExamRequest createExamRequest);
}
