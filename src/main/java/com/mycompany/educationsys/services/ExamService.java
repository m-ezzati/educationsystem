package com.mycompany.educationsys.services;

import com.mycompany.educationsys.dto.AssignQuestionToExamRequest;
import com.mycompany.educationsys.dto.CreateExamRequest;
import com.mycompany.educationsys.entity.exam.Exam;
import com.mycompany.educationsys.entity.question.Option;

import java.util.List;
import java.util.Optional;

public interface ExamService {
    void addExam(Long professorId, Long courseId,
                 CreateExamRequest createExamRequest);
    void deleteExam(Long examId, Long professorId);

    void editExam(Long professorId, Long examId, CreateExamRequest createExamRequest);
    void assignQuestionsToExam( Long professorId,
                                Long examId,
                                List<AssignQuestionToExamRequest> requests);

    List<Exam> findExamByCourseAndStudent(Long studentId, Long courseId);
    Optional<Exam> findById(Long id);
}
