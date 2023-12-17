package com.api.learning.ElearningBE.services.quiz_submission;

import com.api.learning.ElearningBE.constant.ELearningConstant;
import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.answer_question.ReviewAnswerQuestionDto;
import com.api.learning.ElearningBE.dto.quiz_question.ReviewQuizQuestionDto;
import com.api.learning.ElearningBE.dto.quiz_submission.QuizSubmissionDto;
import com.api.learning.ElearningBE.dto.quiz_submission.ReviewQuizSubmissionDto;
import com.api.learning.ElearningBE.exceptions.InvalidException;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.quiz_submission.CreateQuizSubmissionForm;
import com.api.learning.ElearningBE.form.quiz_submission_result.CreateQuizSubmissionResult;
import com.api.learning.ElearningBE.mapper.AnswerQuestionMapper;
import com.api.learning.ElearningBE.mapper.QuizQuestionMapper;
import com.api.learning.ElearningBE.mapper.QuizSubmissionMapper;
import com.api.learning.ElearningBE.repositories.*;
import com.api.learning.ElearningBE.security.impl.UserService;
import com.api.learning.ElearningBE.storage.criteria.QuizSubmissionCriteria;
import com.api.learning.ElearningBE.storage.entities.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class QuizSubmissionServiceImpl implements QuizSubmissionService{

    @Autowired
    private QuizSubmissionRepository quizSubmissionRepository;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private AnswerQuestionRepository answerQuestionRepository;
    @Autowired
    private QuizSubmissionResultRepository quizSubmissionResultRepository;
    @Autowired
    private QuizQuestionRepository quizQuestionRepository;
    @Autowired
    private QuizQuestionMapper quizQuestionMapper;
    @Autowired
    private AnswerQuestionMapper answerQuestionMapper;
    @Autowired
    private QuizSubmissionMapper quizSubmissionMapper;
    @Autowired
    private UserService userService;

    @Override
    public ApiMessageDto<ResponseListDto<List<QuizSubmissionDto>>> list(QuizSubmissionCriteria quizSubmissionCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<QuizSubmissionDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<QuizSubmissionDto>> responseListDto = new ResponseListDto<>();
        Page<QuizSubmission> submissions = quizSubmissionRepository.findAll(quizSubmissionCriteria.getSpecification(),pageable);
        List<QuizSubmissionDto> quizSubmissionDtoS = quizSubmissionMapper.fromEntityToQuizSubmissionDtoList(submissions.getContent());

        responseListDto.setTotalElements(submissions.getTotalElements());
        responseListDto.setContent(quizSubmissionDtoS);
        responseListDto.setPageSize(submissions.getSize());
        responseListDto.setTotalPages(submissions.getTotalPages());
        responseListDto.setPageIndex(submissions.getNumber());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Retrieve submission list successfully");
        return apiMessageDto;
    }

    @Override
    @Transactional
    public ApiMessageDto<QuizSubmissionDto> submit(CreateQuizSubmissionForm createQuizSubmissionForm) {
        ApiMessageDto<QuizSubmissionDto> apiMessageDto = new ApiMessageDto<>();
        Long studentId = userService.getAccountId();
        Quiz quiz = quizRepository.findById(createQuizSubmissionForm.getQuizId())
                .orElseThrow(() -> new NotFoundException(String.format("Quiz with id %s not found", createQuizSubmissionForm.getQuizId())));
        Integer attemptNumber = quizSubmissionRepository.countByStudentIdAndQuizIdAndCourseId(studentId, quiz.getId(), createQuizSubmissionForm.getCourseId());
        if (!quiz.getAttemptNumber().equals(ELearningConstant.QUIZ_QUESTION_ATTEMPT_NUMBER_UNLIMITED) && attemptNumber >= quiz.getAttemptNumber()){
            throw new InvalidException("The maximum number of attempts for the quiz has been exceeded");
        }
        Account student = accountRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(String.format("Student with id %s not found", studentId)));
        Course course = courseRepository.findById(createQuizSubmissionForm.getCourseId())
                .orElseThrow(() -> new NotFoundException(String.format("Course with id %s not found", createQuizSubmissionForm.getCourseId())));

        QuizSubmission submission = new QuizSubmission();
        submission.setTotalTime(createQuizSubmissionForm.getTotalTime());
        submission.setStudent(student);
        submission.setQuiz(quiz);
        submission.setCourse(course);
        quizSubmissionRepository.save(submission);

        List<Long> answerIds = createQuizSubmissionForm.getResults().parallelStream().map(CreateQuizSubmissionResult::getAnswerId).collect(Collectors.toList());
        List<AnswerQuestion> answers = answerQuestionRepository.findAllByIdIn(answerIds);

        List<QuizSubmissionResult> submissionResults = answers.parallelStream()
                .map(answer -> {
                    QuizSubmissionResult submissionResult = new QuizSubmissionResult();
                    submissionResult.setSubmission(submission);
                    submissionResult.setAnswer(answer);
                    submissionResult.setQuestion(answer.getQuestion());
                    return submissionResult;
                }).collect(Collectors.toList());
        quizSubmissionResultRepository.saveAll(submissionResults);

        Double score = calculateQuizSubmissionScore(submission, submissionResults);
        submission.setScore(score);
        quizSubmissionRepository.save(submission);

        QuizSubmissionDto quizSubmissionDto = quizSubmissionMapper.fromEntityToQuizSubmissionDto(submission);
        quizSubmissionDto.setScore(score);

        apiMessageDto.setData(quizSubmissionDto);
        apiMessageDto.setMessage("Submit successfully");
        return apiMessageDto;
    }

    private Double calculateQuizSubmissionScore(QuizSubmission submission, List<QuizSubmissionResult> submissionResults){
        //Group by answer result with questionId
        Map<Long, List<AnswerQuestion>> answerResultsMap = submissionResults.parallelStream()
                .map(QuizSubmissionResult::getAnswer)
                .collect(Collectors.groupingByConcurrent(submissionResult -> submissionResult.getQuestion().getId()));
        List<QuizQuestion> questions = quizQuestionRepository.findAllByQuizId(submission.getQuiz().getId());
        List<Long> questionIds = questions.parallelStream().map(QuizQuestion::getId).
                collect(Collectors.toList());

        Map<Long,List<AnswerQuestion>> answersMap = answerQuestionRepository.findAllByQuestionIdIn(questionIds).parallelStream().
                collect(Collectors.groupingByConcurrent(answer -> answer.getQuestion().getId()));

        AtomicInteger amountQuestionSelected = new AtomicInteger(0);
        questions.forEach( question -> {
            // Single choice
            if (question.getQuestionType().equals(ELearningConstant.QUIZ_QUESTION_TYPE_SINGLE_CHOICE)) {
                List<AnswerQuestion> answerResults = answerResultsMap.getOrDefault(question.getId(), Collections.emptyList());
                answerResults.forEach(answerResult -> {
                    if (answerResult.getIsCorrect().equals(true)) {
                        amountQuestionSelected.incrementAndGet();
                    }
                });
            }else {
                // Multiple choice
                if (question.getQuestionType().equals(ELearningConstant.QUIZ_QUESTION_TYPE_MULTI_CHOICE)){
                    List<AnswerQuestion> multipleAnswerResults = answerResultsMap.getOrDefault(question.getId(), Collections.emptyList());
                    List<AnswerQuestion> multipleAnswerResultsIsCorrect = multipleAnswerResults.stream()
                            .filter(answerIsCorrect -> answerIsCorrect.getIsCorrect().equals(true))
                            .collect(Collectors.toList());
                    List<AnswerQuestion> answerList = answersMap.getOrDefault(question.getId(), Collections.emptyList());
                    List<AnswerQuestion> answerIsCorrectList = answerList.stream()
                            .filter(answerIsCorrect -> answerIsCorrect.getIsCorrect().equals(true))
                            .collect(Collectors.toList());
                    if (!multipleAnswerResultsIsCorrect.isEmpty() && !answerIsCorrectList.isEmpty()) {
                        if (multipleAnswerResultsIsCorrect.size() == answerIsCorrectList.size()
                                && multipleAnswerResults.size() == answerIsCorrectList.size()) {
                            amountQuestionSelected.incrementAndGet();
                        }
                    }
                }
            }
        });
        int finalAmountQuestionSelected = amountQuestionSelected.get();
        return (ELearningConstant.QUIZ_QUESTION_SCORE_DEFAULT * 10.0 * finalAmountQuestionSelected) / questions.size();
    }


//    private Double calculateQuizSubmissionScore(QuizSubmission submission, List<QuizSubmissionResult> submissionResults){
//        //Group by answer result with questionId
//        Map<Long, List<AnswerQuestion>> answerResultsMap = submissionResults.parallelStream()
//                .map(QuizSubmissionResult::getAnswer)
//                .collect(Collectors.groupingByConcurrent(submissionResult -> submissionResult.getQuestion().getId()));
//        List<QuizQuestion> questions = quizQuestionRepository.findAllByQuizId(submission.getQuiz().getId());
//
//        AtomicInteger amountQuestionSelected = new AtomicInteger(0);
//        questions.parallelStream().forEach(question -> {
//            if (question.getQuestionType().equals(ELearningConstant.QUIZ_QUESTION_TYPE_SINGLE_CHOICE)) {
//                List<AnswerQuestion> answerResults = answerResultsMap.getOrDefault(question.getId(), Collections.emptyList());
//                answerResults.forEach(answerResult -> {
//                    if (answerResult.getIsCorrect().equals(true)) {
//                        amountQuestionSelected.incrementAndGet();
//                    }
//                });
//            }
//        });
//        int finalAmountQuestionSelected = amountQuestionSelected.get();
//        return (ELearningConstant.QUIZ_QUESTION_SCORE_DEFAULT * 10.0 * finalAmountQuestionSelected) / questions.size();
//    }

    @Override
    public ApiMessageDto<ReviewQuizSubmissionDto> review(Long id) {
        ApiMessageDto<ReviewQuizSubmissionDto> apiMessageDto = new ApiMessageDto<>();
        QuizSubmission submission = quizSubmissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Quiz submission with id %s not found", id)));
        ReviewQuizSubmissionDto reviewQuizSubmissionDto = quizSubmissionMapper.fromEntityToReviewQuizSubmissionDto(submission);
        //Group by answer result with questionId
        Map<Long, List<AnswerQuestion>> answerResultsMap = quizSubmissionResultRepository.findAllBySubmissionId(submission.getId()).stream()
                .map(QuizSubmissionResult::getAnswer)
                .collect(Collectors.groupingByConcurrent(submissionResult -> submissionResult.getQuestion().getId()));

        List<QuizQuestion> questions = quizQuestionRepository.findAllByQuizId(submission.getQuiz().getId());
        List<Long> questionIds = questions.stream().map(QuizQuestion::getId).collect(Collectors.toList());

        Map<Long, List<AnswerQuestion>> answersMap = answerQuestionRepository.findAllByQuestionIdIn(questionIds).stream()
                .collect(Collectors.groupingByConcurrent(answer -> answer.getQuestion().getId()));

        List<ReviewQuizQuestionDto> questionReviewDtoList = new ArrayList<>();
        questions.parallelStream().forEach(question -> {
            if (question.getQuestionType().equals(ELearningConstant.QUIZ_QUESTION_TYPE_SINGLE_CHOICE)) {
                ReviewQuizQuestionDto questionReviewDto = quizQuestionMapper.fromEntityToReviewQuizQuestionDto(question);
                List<ReviewAnswerQuestionDto> answerReviewDtoList = answersMap.getOrDefault(question.getId(), Collections.emptyList()).stream()
                        .map(answerQuestionMapper::fromEntityToReviewAnswerQuestionDto)
                        .peek(answerReviewDto -> answerReviewDto.setIsSelected(false))
                        .collect(Collectors.toList());

                Map<Long, ReviewAnswerQuestionDto> answerDtoListMap = answerReviewDtoList.stream().collect(Collectors.toConcurrentMap(ReviewAnswerQuestionDto::getId, Function.identity()));
                List<AnswerQuestion> answerResults = answerResultsMap.getOrDefault(question.getId(), Collections.emptyList());
                answerResults.forEach(answerResult -> {
                    answerDtoListMap.get(answerResult.getId()).setIsSelected(true);
                });
                questionReviewDto.setAnswers(answerReviewDtoList);
                questionReviewDtoList.add(questionReviewDto);
            }
            });
        reviewQuizSubmissionDto.setQuestions(questionReviewDtoList);

        apiMessageDto.setData(reviewQuizSubmissionDto);
        apiMessageDto.setMessage("");
        return apiMessageDto;
    }

}
