package com.mycompany.educationsys.services.impl;

import com.mycompany.educationsys.dto.CreateExamRequest;
import com.mycompany.educationsys.entity.Course;
import com.mycompany.educationsys.entity.Exam;
import com.mycompany.educationsys.entity.User;
import com.mycompany.educationsys.exception.CourseNotFoundException;
import com.mycompany.educationsys.exception.ExamNotFoundException;
import com.mycompany.educationsys.exception.ForbiddenActionException;
import com.mycompany.educationsys.repository.CourseRepository;
import com.mycompany.educationsys.repository.ExamRepository;
import com.mycompany.educationsys.repository.UserRepository;
import com.mycompany.educationsys.services.ExamService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public ExamServiceImpl(ExamRepository examRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.examRepository = examRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void addExam(Long professorId, Long courseId, CreateExamRequest createExamRequest) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found"));

        User professor = userRepository.findById(professorId)
                .orElseThrow(() -> new UsernameNotFoundException("Professor not found"));

        if (!isProfessorOwner(course, professorId)) {
            throw new ForbiddenActionException("Professor is not owner the course!");
        }

        Exam exam = new Exam();
        exam.setTitle(createExamRequest.getTitle());
        exam.setDescription(createExamRequest.getDescription());
        exam.setDuration(createExamRequest.getDuration());
        exam.setCourse(course);
        exam.setProfessor(professor);

        examRepository.save(exam);

    }

    @Override
    public void deleteExam(Long examId, Long professorId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException(examId));
        Course course = exam.getCourse();

        if (!isProfessorOwner(course, professorId)){
            throw new ForbiddenActionException("Professor is not owner the course!");
        }
        examRepository.delete(exam);
    }

    @Override
    public void editExam(Long professorId, Long examId, CreateExamRequest createExamRequest) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException(examId));
        Course course = exam.getCourse();

        if (!isProfessorOwner(course, professorId)){
            throw new ForbiddenActionException("Professor is not owner the course!");
        }

        exam.setTitle(createExamRequest.getTitle());
        exam.setDescription(createExamRequest.getDescription());
        exam.setDuration(createExamRequest.getDuration());

        examRepository.save(exam);
    }

    private boolean isProfessorOwner(Course course, Long professorId) {
        return course.getTeacher().getId().equals(professorId);
    }
}
