package com.mycompany.educationsys.repository;

import com.mycompany.educationsys.entity.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
