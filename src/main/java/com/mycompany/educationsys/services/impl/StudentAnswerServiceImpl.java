package com.mycompany.educationsys.services.impl;

import com.mycompany.educationsys.dto.DescriptiveScoreDto;
import com.mycompany.educationsys.dto.StudentAnswerRegister;
import com.mycompany.educationsys.entity.User;
import com.mycompany.educationsys.entity.exam.ExamQuestion;
import com.mycompany.educationsys.entity.exam.StudentAnswer;
import com.mycompany.educationsys.entity.exam.StudentExam;
import com.mycompany.educationsys.entity.exam.enums.ExamStatus;
import com.mycompany.educationsys.entity.question.DescriptiveQuestion;
import com.mycompany.educationsys.entity.question.MultipleChoiceQuestion;
import com.mycompany.educationsys.entity.question.Option;
import com.mycompany.educationsys.entity.question.Question;
import com.mycompany.educationsys.exception.ForbiddenActionException;
import com.mycompany.educationsys.repository.ExamQuestionRepository;
import com.mycompany.educationsys.repository.QuestionRepository;
import com.mycompany.educationsys.repository.StudentAnswerRepository;
import com.mycompany.educationsys.repository.StudentExamRepository;
import com.mycompany.educationsys.services.StudentAnswerService;
import com.mycompany.educationsys.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class StudentAnswerServiceImpl implements StudentAnswerService {

    private final StudentExamRepository studentExamRepository;
    private final QuestionRepository questionRepository;
    private final StudentAnswerRepository studentAnswerRepository;
    private final ExamQuestionRepository examQuestionRepository;

    public StudentAnswerServiceImpl(StudentExamRepository studentExamRepository, QuestionRepository questionRepository,
                                    StudentAnswerRepository studentAnswerRepository, ExamQuestionRepository examQuestionRepository) {
        this.studentExamRepository = studentExamRepository;
        this.questionRepository = questionRepository;
        this.studentAnswerRepository = studentAnswerRepository;
        this.examQuestionRepository = examQuestionRepository;
    }


    @Override
    public void studentAnswer(StudentAnswerRegister answer, Long studentId) {

        StudentExam studentExam = studentExamRepository.findById(answer.getStudentExamId())
                .orElseThrow(() -> new EntityNotFoundException("student exam not found"));

        Question question = questionRepository.findById(answer.getQuestionId())
                .orElseThrow(() -> new EntityNotFoundException("question not found"));

        ExamQuestion examQuestion = examQuestionRepository
                .findByExamAndQuestion(studentExam.getExam(), question)
                .orElseThrow(() ->  new IllegalStateException("ExamQuestion not found"));

        if (!questionExistsInExam(studentExam, question)) {
            throw new IllegalStateException("This question is not in the exam!");
        }

//        if(alreadyAnswered(studentExam, question)){
//            throw new IllegalStateException("The student already answered to this question!");
//
//        }
        StudentAnswer studentAnswer = studentAnswerRepository
                .findByStudentExamIdAndQuestionId(
                        studentExam.getId(),
                        question.getId()
                )
                .orElseGet(StudentAnswer::new);

        studentAnswer.setStudentExam(studentExam);
        studentAnswer.setQuestion(question);


        if (question instanceof MultipleChoiceQuestion mcq) {

            if (answer.getSelectedOptionId() == null) {
                throw new IllegalArgumentException("Option must be selected for multiple choice question");
            }

            Option selectedOption = mcq.getOptions()
                    .stream()
                    .filter(o -> o.getId().equals(answer.getSelectedOptionId()))
                    .findFirst()
                    .orElseThrow(() ->
                            new IllegalArgumentException("Selected option is not valid for this question")
                    );

            studentAnswer.setSelectedOption(selectedOption);
            studentAnswer.setAnswer(null);

            if (mcq.getCorrectOption().getId().equals(selectedOption.getId())) {
                studentAnswer.setEarnedScore(
                        examQuestion.getScore().intValue()
                );
            } else {
                studentAnswer.setEarnedScore(0);
            }

        } else if (question instanceof DescriptiveQuestion) {

            if (answer.getAnswer() == null || answer.getAnswer().isBlank()) {
                throw new IllegalArgumentException("Answer text cannot be empty");
            }

            studentAnswer.setAnswer(answer.getAnswer());
            studentAnswer.setSelectedOption(null);
        }

        studentAnswerRepository.save(studentAnswer);

    }

    @Transactional
    public void scoreDescriptiveAnswer(DescriptiveScoreDto dto) {

        StudentAnswer answer =
                studentAnswerRepository.findById(dto.getStudentAnswerId())
                        .orElseThrow(() ->  new IllegalArgumentException("Answer not found"));

        ExamQuestion examQuestion =
                examQuestionRepository.findByExamAndQuestion(
                        answer.getStudentExam().getExam(),
                        answer.getQuestion()
                ).orElseThrow(() ->  new IllegalStateException("ExamQuestion not found"));

        double maxScore = examQuestion.getScore();

        if (dto.getScore() > maxScore) {
            throw new ForbiddenActionException(
                    "Score cannot be greater than question max score"
            );
        }

        answer.setEarnedScore(dto.getScore());

        StudentExam studentExam = answer.getStudentExam();

        if(!studentExam.getStatus().equals(ExamStatus.FINISHED)){
            throw new ForbiddenActionException(
                    "The exam not finished yet!"
            );
        }
        Integer newTotalScore =
                studentAnswerRepository
                        .sumEarnedScoreByStudentExamId(studentExam.getId());

        studentExam.setScore(newTotalScore);


    }



    private boolean questionExistsInExam(StudentExam studentExam, Question question) {
        return studentExam.getExam()
                .getExamQuestions()
                .stream()
                .anyMatch(eq -> eq.getQuestion().getId().equals(question.getId()));

    }

    private boolean alreadyAnswered(StudentExam studentExam, Question question) {
        return studentAnswerRepository
                .existsByStudentExamIdAndQuestionId(
                        studentExam.getId(),
                        question.getId()
                );
    }
}
