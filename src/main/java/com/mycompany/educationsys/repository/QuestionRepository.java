package com.mycompany.educationsys.repository;

import com.mycompany.educationsys.entity.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("""
            SELECT q FROM Question q
            JOIN q.questionBank qb
            LEFT JOIN FETCH q.options
            WHERE qb IN (
                        SELECT u.questionBank FROM User u WHERE u.id = :professorId
                        )
            """)
    List<Question> findAllByProfessorId(@Param("professorId") Long professorId);

    @Query("""
            SELECT q FROM Question q
            JOIN q.questionBank qb
            JOIN q.course c ON c.id = :courseId
            LEFT JOIN FETCH q.options
            WHERE qb IN (
                        SELECT u.questionBank FROM User u WHERE u.id = :professorId
                        )
            """)
    List<Question> findAllByProfessorIdAndCourseId(
            @Param("professorId") Long professorId,
            @Param("courseId") Long courseId
            );


}
