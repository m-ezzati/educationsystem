package com.mycompany.educationsys.services.impl;

import com.mycompany.educationsys.dto.QuestionDto;
import com.mycompany.educationsys.dto.StudentExamResultDto;
import com.mycompany.educationsys.entity.Course;
import com.mycompany.educationsys.entity.User;
import com.mycompany.educationsys.entity.exam.Exam;
import com.mycompany.educationsys.entity.exam.ExamQuestion;
import com.mycompany.educationsys.entity.exam.StudentAnswer;
import com.mycompany.educationsys.entity.exam.StudentExam;
import com.mycompany.educationsys.entity.exam.enums.ExamStatus;
import com.mycompany.educationsys.entity.question.MultipleChoiceQuestion;
import com.mycompany.educationsys.entity.question.Question;
import com.mycompany.educationsys.exception.ForbiddenActionException;
import com.mycompany.educationsys.exception.course.CourseNotFoundException;
import com.mycompany.educationsys.mapper.QuestionMapper;
import com.mycompany.educationsys.repository.ExamQuestionRepository;
import com.mycompany.educationsys.repository.StudentAnswerRepository;
import com.mycompany.educationsys.repository.StudentExamRepository;
import com.mycompany.educationsys.services.ExamService;
import com.mycompany.educationsys.services.StudentExamService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentExamServiceImpl implements StudentExamService {

    private final StudentExamRepository studentExamRepository;
    private final UserServiceImpl userServiceImpl;
    private final EnrollmentServiceImpl enrollmentServiceImpl;
    private final ExamService examService;
    private final QuestionMapper questionMapper;
    private final StudentAnswerRepository studentAnswerRepository;
    private final ExamQuestionRepository examQuestionRepository;


    public StudentExamServiceImpl(StudentExamRepository studentExamRepository, CourseServiceImpl courseServiceImpl, UserServiceImpl userServiceImpl, EnrollmentServiceImpl enrollmentServiceImpl, ExamService examService, QuestionMapper questionMapper, StudentAnswerRepository studentAnswerRepository, ExamQuestionRepository examQuestionRepository) {
        this.studentExamRepository = studentExamRepository;
        this.userServiceImpl = userServiceImpl;
        this.enrollmentServiceImpl = enrollmentServiceImpl;
        this.examService = examService;
        this.questionMapper = questionMapper;
        this.studentAnswerRepository = studentAnswerRepository;
        this.examQuestionRepository = examQuestionRepository;
    }

    public StudentExam startExam(Long examId, Long studentId) {
        Exam exam = getExam(examId);

        Course course = getCourse(exam);

        User student = userServiceImpl.getUserById(studentId);

        StudentExam studentExam = studentExamRepository.findByExamAndStudent(exam, student)
                .orElseGet(() -> {
                    StudentExam newAttempt = new StudentExam();
                    newAttempt.setExam(exam);
                    newAttempt.setStudent(student);
                    return newAttempt;
                });

        if (!enrollmentServiceImpl.isStudentEnrolledCourse(student, course)) {
            throw new ForbiddenActionException("Student ID " + studentId + " is not enrolled in this course.");
        }

        if (studentExam.getStatus() == ExamStatus.IN_PROGRESS) {
            throw new ForbiddenActionException("Exam already started.");
        }

        if (studentExam.getStatus() == ExamStatus.FINISHED) {
            throw new ForbiddenActionException("Exam already finished.");
        }

        if (studentExam.getTryCount() >= 1) {
            throw new ForbiddenActionException("No attempts left.");
        }

        studentExam.setTryCount((short) (studentExam.getTryCount() + 1));
        studentExam.setStartedAt(LocalDateTime.now());
        studentExam.setStatus(ExamStatus.IN_PROGRESS);

        return studentExamRepository.save(studentExam);
    }

    public StudentExam finishExam(Long examId, Long studentId) {
        System.out.println("service");
        Exam exam = getExam(examId);

        User student = userServiceImpl.getUserById(studentId);

        StudentExam studentExam = studentExamRepository.findByExamAndStudent(exam, student)
                .orElseThrow(() -> new ForbiddenActionException("The student exam doesn't exists"));

        if (studentExam.getStatus() == ExamStatus.FINISHED) {
            throw new ForbiddenActionException("The exam already finished");
        }


        // calculate score
        int score = studentAnswerRepository.calculateMcqScore(studentExam.getId());
        studentExam.setScore(score);

        int totalScore = examQuestionRepository
                .calculateTotalScore(exam.getId());
        studentExam.setTotalScore(totalScore);

        studentExam.setStatus(ExamStatus.FINISHED);
        studentExam.setFinishedAt(LocalDateTime.now());

        return studentExamRepository.save(studentExam);
    }

    @Override
    public List<QuestionDto> getExamQuestions(Long studentExamId, Long studentId) {
        Exam exam = getExam(studentExamId);

        User student = userServiceImpl.getUserById(studentId);

        StudentExam studentExam = studentExamRepository.findByExamAndStudent(exam, student)
                .orElseThrow(() -> new ForbiddenActionException("The student exam doesn't exists"));


        return studentExam.getExam().getExamQuestions()
                .stream()
                .map(ExamQuestion::getQuestion)
                .map(questionMapper::toDto)
                .toList();

    }

    private Exam getExam(Long examId) {
        return examService.findById(examId)
                .orElseThrow(() -> new EntityNotFoundException("Exam with ID " + examId + " not found."));
    }

    private Course getCourse(Exam exam) {
        if (exam.getCourse() == null) {
            throw new CourseNotFoundException("The course associated with Exam ID " + exam.getId() + " is null.");
        }
        return exam.getCourse();

    }

    @Override
    public List<StudentExamResultDto> getExamResultsForProfessor(Long examId) {

        List<StudentExam> studentExams =
                studentExamRepository.findByExamId(examId);

        return studentExams.stream()
                .map(se -> {
                    StudentExamResultDto dto = new StudentExamResultDto();
                    dto.setStudentExamId(se.getId());
                    dto.setStudentId(se.getStudent().getId());
                    dto.setStudentName(se.getStudent().getUsername());
                    dto.setStatus(se.getStatus());
                    dto.setScore(
                            se.getStatus() == ExamStatus.FINISHED
                                    ? se.getScore()
                                    : null
                    );
                    dto.setTotalScore(se.getTotalScore());
                    dto.setFinishedAt(se.getFinishedAt());
                    return dto;
                })
                .toList();
    }

//    private int calculateMcqScore(Long studentExamId) {
//
//        List<StudentAnswer> studentAnswers = studentAnswerRepository.
//                findMcqAnswersByStudentExamId(studentExamId);
//
//        System.out.println("******");
//
//        studentAnswers.forEach(a ->
//                System.out.println(a.getQuestion().getClass())
//        );
//
//        return studentAnswers.stream()
//                .filter(a -> a.getSelectedOption() != null)
//                .filter(a -> {
//                    MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) a.getQuestion();
//                    return mcq.getCorrectOption().getId()
//                            .equals(a.getSelectedOption().getId());
//                })
//                .mapToInt(a -> 1)
//                .sum();
//    }

}




