package com.mycompany.educationsys.repository;

import com.mycompany.educationsys.entity.question.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OptionRepository extends JpaRepository<Option, Long> {
    Optional<Option> findByIdAndQuestionId(Long optionId, Long questionId);
}
