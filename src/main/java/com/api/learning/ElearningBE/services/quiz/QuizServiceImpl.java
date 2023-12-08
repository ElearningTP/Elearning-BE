package com.api.learning.ElearningBE.services.quiz;

import com.api.learning.ElearningBE.constant.ELearningConstant;
import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.answer_question.StartQuizAnswerQuestionDto;
import com.api.learning.ElearningBE.dto.quiz.QuizAdminDto;
import com.api.learning.ElearningBE.dto.quiz.QuizDto;
import com.api.learning.ElearningBE.dto.quiz.StartQuizDto;
import com.api.learning.ElearningBE.dto.quiz_question.StartQuizQuestionDto;
import com.api.learning.ElearningBE.exceptions.InvalidException;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.quiz.CreateQuizForm;
import com.api.learning.ElearningBE.form.quiz.UpdateQuizForm;
import com.api.learning.ElearningBE.mapper.*;
import com.api.learning.ElearningBE.repositories.*;
import com.api.learning.ElearningBE.security.impl.UserService;
import com.api.learning.ElearningBE.storage.criteria.QuizCriteria;
import com.api.learning.ElearningBE.storage.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService{

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuizMapper quizMapper;
    @Autowired
    private ModulesRepository modulesRepository;
    @Autowired
    private ModulesMapper modulesMapper;
    @Autowired
    private QuizQuestionRepository quizQuestionRepository;
    @Autowired
    private QuizQuestionMapper quizQuestionMapper;
    @Autowired
    private AnswerQuestionRepository answerQuestionRepository;
    @Autowired
    private AnswerQuestionMapper answerQuestionMapper;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private QuizSubmissionRepository quizSubmissionRepository;
    @Autowired
    private QuizSubmissionMapper quizSubmissionMapper;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserService userService;

    @Override
    public ApiMessageDto<StartQuizDto> start(Long id, Long courseId) {
        ApiMessageDto<StartQuizDto> apiMessageDto = new ApiMessageDto<>();
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Quiz with id %s not found", id)));
        if (!courseRepository.existsById(courseId)){
            throw new NotFoundException(String.format("Course with id %s not found", courseId));
        }
        Long studentId = userService.getAccountId();
        Integer attemptNumber = quizSubmissionRepository.countByStudentIdAndQuizIdAndCourseId(studentId, quiz.getId(), courseId);
        if (!quiz.getAttemptNumber().equals(ELearningConstant.QUIZ_QUESTION_ATTEMPT_NUMBER_UNLIMITED) && attemptNumber >= quiz.getAttemptNumber()){
            throw new InvalidException("The maximum number of attempts for the quiz has been exceeded");
        }
        List<QuizQuestion> questions = quizQuestionRepository.findAllByQuizId(quiz.getId());
        List<Long> questionIds = questions.stream().map(QuizQuestion::getId).collect(Collectors.toList());
        List<AnswerQuestion> answerQuestions = answerQuestionRepository.findAllByQuestionIdIn(questionIds);

        // Group by list answer with question id
        Map<Long, List<AnswerQuestion>> mapAnswerGroupByQuestionId = answerQuestions.stream()
                .collect(Collectors.groupingBy(answerQuestion -> answerQuestion.getQuestion().getId()));

        List<StartQuizQuestionDto> quizQuestionDtoList = questions.stream()
                .map(question -> {
                    StartQuizQuestionDto startQuizQuestionDto = quizQuestionMapper.fromEntityToStartQuizQuestionDto(question);
                    List<StartQuizAnswerQuestionDto> answerQuestionDtoList = answerQuestionMapper.fromEntityToStartQuizAnswerQuestionDtoList(
                            mapAnswerGroupByQuestionId.getOrDefault(question.getId(),Collections.emptyList()));
                    //Shuffle answer
                    Collections.shuffle(answerQuestionDtoList);

                    startQuizQuestionDto.setAnswers(answerQuestionDtoList);
                    return startQuizQuestionDto;
                }).collect(Collectors.toList());

        // Shuffle question
        Collections.shuffle(quizQuestionDtoList);

        StartQuizDto quizDto = quizMapper.fromEntityToStartQuizDto(quiz);
        quizDto.setQuestions(quizQuestionDtoList);

        apiMessageDto.setData(quizDto);
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<ResponseListDto<List<QuizDto>>> list(QuizCriteria quizCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<QuizDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<QuizDto>> responseListDto = new ResponseListDto<>();
        Page<Quiz> quizzes = quizRepository.findAll(quizCriteria.getSpecification(),pageable);
        List<QuizDto> quizDtoS = quizMapper.fromEntityToQuizDtoList(quizzes.getContent());

        responseListDto.setContent(quizDtoS);
        responseListDto.setTotalElements(quizzes.getTotalElements());
        responseListDto.setTotalPages(quizzes.getTotalPages());
        responseListDto.setPageSize(quizzes.getSize());
        responseListDto.setPageIndex(quizzes.getNumber());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Retrieve quiz list successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<QuizAdminDto> retrieve(Long id, Long courseId) {
        ApiMessageDto<QuizAdminDto> apiMessageDto = new ApiMessageDto<>();
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Quiz with id %s not found", id)));
        QuizAdminDto quizAdminDto = quizMapper.fromEntityToQuizAdminDto(quiz);
        if (courseId != null) {
            Long studentId = userService.getAccountId();
            List<QuizSubmission> quizSubmissions = quizSubmissionRepository.findAllByQuizIdAndStudentIdAndCourseId(quiz.getId(), studentId, courseId);
            quizAdminDto.setQuizSubmissionInfo(quizSubmissionMapper.fromEntityToQuizSubmissionDtoList(quizSubmissions));
        }

        apiMessageDto.setData(quizAdminDto);
        apiMessageDto.setMessage("Retrieve quiz successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<QuizDto> create(CreateQuizForm createQuizForm) {
        ApiMessageDto<QuizDto> apiMessageDto = new ApiMessageDto<>();
        Modules modules = modulesRepository.findById(createQuizForm.getModulesId())
                .orElseThrow(() -> new NotFoundException(String.format("Modules with id %s not found", createQuizForm.getModulesId())));
        Quiz quiz = quizMapper.fromCreateQuizFormToEntity(createQuizForm);
        quiz.setModules(modules);
        quizRepository.save(quiz);
        QuizDto quizDto = quizMapper.fromEntityToQuizDto(quiz);

        apiMessageDto.setData(quizDto);
        apiMessageDto.setMessage("Create quiz successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<QuizDto> update(UpdateQuizForm updateQuizForm) {
        ApiMessageDto<QuizDto> apiMessageDto = new ApiMessageDto<>();
        Quiz quiz = quizRepository.findById(updateQuizForm.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Quiz with id %s not found", updateQuizForm.getId())));
        quizMapper.fromUpdateQuizFormToEntity(updateQuizForm,quiz);
        quizRepository.save(quiz);
        QuizDto quizDto = quizMapper.fromEntityToQuizDto(quiz);

        apiMessageDto.setData(quizDto);
        apiMessageDto.setMessage("Update quiz successfully");
        return apiMessageDto;
    }

    @Override
    @Transactional
    public ApiMessageDto<String> delete(Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Quiz with id %s not found", id)));
        answerQuestionRepository.deleteAllByQuizId(quiz.getId());
        quizQuestionRepository.deleteAllByQuizId(quiz.getId());
        quizRepository.delete(quiz);

        apiMessageDto.setMessage("Delete quiz successfully");
        return apiMessageDto;
    }
}
