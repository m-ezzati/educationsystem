package com.mycompany.educationsys.repository;

import com.mycompany.educationsys.entity.exam.Exam;
import com.mycompany.educationsys.entity.exam.ExamQuestion;
import com.mycompany.educationsys.entity.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Long> {

    boolean existsByExamIdAndQuestionId(Long examId, Long questionId);

    Optional<ExamQuestion> findByExamAndQuestion(Exam exam, Question question);

    @Query("""
            select coalesce(sum(eq.score), 0)
            from ExamQuestion eq
            where eq.exam.id = :examId
            """)
    int calculateTotalScore(@Param("examId") Long examId);
}
