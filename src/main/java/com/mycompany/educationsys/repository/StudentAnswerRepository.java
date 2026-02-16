package com.mycompany.educationsys.repository;

import com.mycompany.educationsys.entity.exam.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {
    boolean existsByStudentExamIdAndQuestionId(Long studentExamId, Long questionId);

    Optional<StudentAnswer> findByStudentExamIdAndQuestionId(Long studentExamId, Long questionId);

    @Query("""
                        select sa
                        from StudentAnswer sa
                        join sa.question q
                        where sa.studentExam.id = :studentExamId
                         and type(q) = MultipleChoiceQuestion
            """)
    List<StudentAnswer> findMcqAnswersByStudentExamId(
            @Param("studentExamId") Long studentExamId
    );

    @Query("""
            select coalesce(sum(eq.score), 0)
            from StudentAnswer sa
            join sa.question q
            join treat(q as MultipleChoiceQuestion) mcq
            join ExamQuestion eq
                 on eq.question.id = q.id
                and eq.exam.id = sa.studentExam.exam.id
            where sa.studentExam.id = :studentExamId
            and sa.selectedOption.id = mcq.correctOption.id
            """)
    int calculateMcqScore(@Param("studentExamId") Long studentExamId);

    @Query("""
            select coalesce(sum(sa.earnedScore), 0)
            from StudentAnswer sa
            where sa.studentExam.id = :studentExamId
            """)
    Integer sumEarnedScoreByStudentExamId(Long studentExamId);
}
