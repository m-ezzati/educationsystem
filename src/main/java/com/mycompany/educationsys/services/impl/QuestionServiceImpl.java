package com.mycompany.educationsys.services.impl;

import com.mycompany.educationsys.dto.QuestionDto;
import com.mycompany.educationsys.entity.Course;
import com.mycompany.educationsys.entity.QuestionBank;
import com.mycompany.educationsys.entity.User;
import com.mycompany.educationsys.entity.question.MultipleChoiceQuestion;
import com.mycompany.educationsys.entity.question.Question;
import com.mycompany.educationsys.exception.question.InvalidMultipleChoiceQuestion;
import com.mycompany.educationsys.mapper.QuestionMapper;
import com.mycompany.educationsys.repository.CourseRepository;
import com.mycompany.educationsys.repository.QuestionRepository;
import com.mycompany.educationsys.repository.UserRepository;
import com.mycompany.educationsys.services.QuestionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final UserRepository userRepository;
    private final QuestionMapper questionMapper;
    private final QuestionRepository questionRepository;
    private final CourseRepository courseRepository;

    public QuestionServiceImpl(UserRepository userRepository, QuestionMapper questionMapper, QuestionRepository questionRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.questionMapper = questionMapper;
        this.questionRepository = questionRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public void addQuestion(Long professorId, Long courseId, QuestionDto questionRequest) {
        if (questionRequest.getQuestionType() == null || questionRequest.getQuestionType().isBlank()) {
            throw new IllegalArgumentException("The question type is necessary!");
        }
        User professor = userRepository.findById(professorId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found" + professorId));

        Question question = questionMapper.toEntity(questionRequest);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new EntityNotFoundException("Course not found!"));

        if (question instanceof MultipleChoiceQuestion mcq) {
            if (mcq.getOptions() == null || mcq.getOptions().isEmpty() || mcq.getOptions().size() < 2) {
                System.out.println("inner if");
                throw new InvalidMultipleChoiceQuestion();
            }
        }

        if (professor.getQuestionBank() == null) {
            professor.setQuestionBank(new QuestionBank());
        }

        QuestionBank bank = professor.getQuestionBank();

        question.setQuestionBank(bank);
        bank.getQuestions().add(question);

        question.setCourse(course);

        userRepository.save(professor);

    }

    @Override
    public List<QuestionDto> findProfessorQuestion(Long professorId) {
        return questionToDto(questionRepository
                .findAllByProfessorId(professorId));
    }

    @Override
    public List<QuestionDto> findProfessorQuestionForCourse(Long professorId, Long courseId) {
        return questionToDto(questionRepository
                .findAllByProfessorIdAndCourseId(professorId, courseId));
    }

    private List<QuestionDto> questionToDto(List<Question> questions) {
        return questions.stream()
                .map(questionMapper::toDto)
                .toList();
    }
}
