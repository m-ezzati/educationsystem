package com.mycompany.educationsys.repository;

import com.mycompany.educationsys.entity.exam.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByCourseId(Long courseId);
    List<Exam> findByCourseIdAndProfessorId(Long courseId, Long professorId);
}
