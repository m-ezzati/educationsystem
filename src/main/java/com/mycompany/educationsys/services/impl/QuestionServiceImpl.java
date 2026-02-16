package com.mycompany.educationsys.services.impl;

import com.mycompany.educationsys.dto.QuestionDto;
import com.mycompany.educationsys.entity.Course;
import com.mycompany.educationsys.entity.QuestionBank;
import com.mycompany.educationsys.entity.User;
import com.mycompany.educationsys.entity.question.MultipleChoiceQuestion;
import com.mycompany.educationsys.entity.question.Question;
import com.mycompany.educationsys.exception.ForbiddenActionException;
import com.mycompany.educationsys.exception.question.InvalidMultipleChoiceQuestion;
import com.mycompany.educationsys.mapper.QuestionMapper;
import com.mycompany.educationsys.repository.CourseRepository;
import com.mycompany.educationsys.repository.QuestionRepository;
import com.mycompany.educationsys.repository.UserRepository;
import com.mycompany.educationsys.services.CourseService;
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
    private final CourseServiceImpl courseServiceImpl;
    private final UserServiceImpl userServiceImpl;

    public QuestionServiceImpl(UserRepository userRepository, QuestionMapper questionMapper, QuestionRepository questionRepository, CourseServiceImpl courseServiceImpl, UserServiceImpl userServiceImpl) {
        this.userRepository = userRepository;
        this.questionMapper = questionMapper;
        this.questionRepository = questionRepository;
        this.courseServiceImpl = courseServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    @Transactional
    public void addQuestion(Long professorId, Long courseId, QuestionDto questionDto) {
        validateQuestionRequest(questionDto);

        Question question = questionMapper.toEntity(questionDto);
        validateMultipleChoiceQuestion(question);

        User professor = userServiceImpl.getUserById(professorId);

        Course course = courseServiceImpl.getCourseById(courseId);

        if(!userServiceImpl.isProfessorOwner(course, professorId)){
            throw new ForbiddenActionException("The professor is not owner the course");
        }

        QuestionBank bank = getOrCreateQuestionBank(professor);

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

    private void validateQuestionRequest(QuestionDto dto) {
        if (dto.getQuestionType() == null || dto.getQuestionType().isBlank()) {
            throw new IllegalArgumentException("The question type is necessary!");
        }
    }

    private void validateMultipleChoiceQuestion(Question question) {
        if (question instanceof MultipleChoiceQuestion mcq) {
            if (mcq.getOptions() == null || mcq.getOptions().size() < 2) {
                throw new InvalidMultipleChoiceQuestion();
            }
        }
    }
    private QuestionBank getOrCreateQuestionBank(User professor) {
        if (professor.getQuestionBank() == null) {
            professor.setQuestionBank(new QuestionBank());
        }
        return professor.getQuestionBank();
    }

}
