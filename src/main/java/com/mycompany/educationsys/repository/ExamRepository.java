package com.mycompany.educationsys.repository;

import com.mycompany.educationsys.entity.Course;
import com.mycompany.educationsys.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByCourseId(Long courseId);
    List<Exam> findByCourseIdAndProfessorId(Long courseId, Long professorId);

}
