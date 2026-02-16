package com.mycompany.educationsys.services.impl;

import com.mycompany.educationsys.dto.AssignQuestionToExamRequest;
import com.mycompany.educationsys.dto.CreateExamRequest;
import com.mycompany.educationsys.entity.Course;
import com.mycompany.educationsys.entity.exam.Exam;
import com.mycompany.educationsys.entity.User;
import com.mycompany.educationsys.entity.exam.ExamQuestion;
import com.mycompany.educationsys.entity.question.Question;
import com.mycompany.educationsys.exception.AccessDeniedException;
import com.mycompany.educationsys.exception.course.CourseNotFoundException;
import com.mycompany.educationsys.exception.exam.ExamNotFoundException;
import com.mycompany.educationsys.exception.ForbiddenActionException;
import com.mycompany.educationsys.repository.*;
import com.mycompany.educationsys.services.ExamService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ExamQuestionRepository examQuestionRepository;
    private final QuestionRepository questionRepository;
    private final EnrollmentServiceImpl enrollmentServiceImpl;
    private final UserServiceImpl userServiceImpl;
    private final CourseServiceImpl courseServiceImpl;

    public ExamServiceImpl(ExamRepository examRepository, CourseRepository courseRepository, UserRepository userRepository, ExamQuestionRepository examQuestionRepository, QuestionRepository questionRepository, EnrollmentServiceImpl enrollmentServiceImpl, UserServiceImpl userServiceImpl, CourseServiceImpl courseServiceImpl) {
        this.examRepository = examRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.examQuestionRepository = examQuestionRepository;
        this.questionRepository = questionRepository;
        this.enrollmentServiceImpl = enrollmentServiceImpl;
        this.userServiceImpl = userServiceImpl;
        this.courseServiceImpl = courseServiceImpl;
    }

    @Override
    @Transactional
    public void addExam(Long professorId, Long courseId, CreateExamRequest createExamRequest) {
        System.out.println("service");
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found"));
        System.out.println("service course" + course);

        User professor = userRepository.findById(professorId)
                .orElseThrow(() -> new UsernameNotFoundException("Professor not found"));
        System.out.println("prooo" + professor);

        if (!isProfessorOwner(course, professorId)) {
            throw new ForbiddenActionException("Professor is not owner the course!");
        }

        Exam exam = new Exam();
        exam.setTitle(createExamRequest.getTitle());
        exam.setDescription(createExamRequest.getDescription());
        exam.setDuration(createExamRequest.getDuration());
        exam.setCourse(course);
        exam.setProfessor(professor);

        System.out.println("ecam" + exam);
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

    @Override
    public void assignQuestionsToExam(Long professorId, Long examId, List<AssignQuestionToExamRequest> requests) {

        Exam exam = getExam(examId);
        validateProfessorAccess(professorId, exam);


        System.out.println("before for");
        for (AssignQuestionToExamRequest req : requests) {

            if (examQuestionRepository
                    .existsByExamIdAndQuestionId(examId, req.getQuestionId())) {
                continue;
            }
            System.out.println("before finnnnd");

            Question question = questionRepository.findById(req.getQuestionId())
                    .orElseThrow(() ->
                            new EntityNotFoundException("Question not found: " + req.getQuestionId()));

            ExamQuestion examQuestion = new ExamQuestion();
            examQuestion.setExam(exam);
            examQuestion.setQuestion(question);
            examQuestion.setScore(req.getScore());
            exam.getExamQuestions().add(examQuestion);
        }

        examRepository.save(exam);
    }

    @Override
    public List<Exam> findExamByCourseAndStudent(Long studentId, Long courseId) {

        User student = userServiceImpl.getUserById(studentId);
        Course course = courseServiceImpl.getCourseById(courseId);

        if(!enrollmentServiceImpl.isStudentEnrolledCourse(student, course)){
            throw new ForbiddenActionException("The student doesn't enroll in course!");
        }
        return examRepository.findByCourseId(courseId);

    }

    @Override
    public Optional<Exam> findById(Long id) {
        return examRepository.findById(id);
    }

    private Exam getExam(Long examId) {
        return examRepository.findById(examId)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found"));
    }

    private void validateProfessorAccess(Long professorId, Exam exam) {
        if (!exam.getProfessor().getId().equals(professorId)) {
            throw new AccessDeniedException("Professor has no access to this exam");
        }
    }

    private boolean isProfessorOwner(Course course, Long professorId) {
        return course.getTeacher().getId().equals(professorId);
    }
}
