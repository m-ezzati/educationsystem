package com.mycompany.educationsys.services;

import com.mycompany.educationsys.dto.QuestionDto;
import com.mycompany.educationsys.dto.StudentExamResultDto;
import com.mycompany.educationsys.entity.exam.StudentExam;

import java.util.List;

public interface StudentExamService {
    StudentExam startExam(Long studentExamId, Long studentId);
    StudentExam finishExam(Long studentExamId, Long studentId);
    List<QuestionDto> getExamQuestions(Long studentExamId, Long studentId);
    List<StudentExamResultDto> getExamResultsForProfessor(Long examId);
}
