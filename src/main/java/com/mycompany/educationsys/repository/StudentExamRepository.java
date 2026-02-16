package com.mycompany.educationsys.repository;

import com.mycompany.educationsys.entity.User;
import com.mycompany.educationsys.entity.exam.Exam;
import com.mycompany.educationsys.entity.exam.StudentExam;
import com.mycompany.educationsys.entity.exam.enums.ExamStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentExamRepository extends JpaRepository<StudentExam, Long> {
    Optional<StudentExam> findById(Long id);
    Optional<StudentExam> findByExamAndStudent(Exam exam, User student);
    List<StudentExam> findByStatus(ExamStatus examStatus);
    List<StudentExam> findByExamId(Long examId);



}
