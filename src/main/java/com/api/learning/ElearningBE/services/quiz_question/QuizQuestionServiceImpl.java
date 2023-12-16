package com.api.learning.ElearningBE.services.quiz_question;

import com.api.learning.ElearningBE.constant.ELearningConstant;
import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.answer_question.AnswerQuestionDto;
import com.api.learning.ElearningBE.dto.quiz_question.QuizQuestionAdminDto;
import com.api.learning.ElearningBE.dto.quiz_question.QuizQuestionDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.answer_question.UpdateAnswerQuestionForm;
import com.api.learning.ElearningBE.form.quiz_question.CreateQuizQuestionForm;
import com.api.learning.ElearningBE.form.quiz_question.UpdateQuizQuestionForm;
import com.api.learning.ElearningBE.mapper.AnswerQuestionMapper;
import com.api.learning.ElearningBE.mapper.QuizQuestionMapper;
import com.api.learning.ElearningBE.repositories.AnswerQuestionRepository;
import com.api.learning.ElearningBE.repositories.QuizQuestionRepository;
import com.api.learning.ElearningBE.repositories.QuizRepository;
import com.api.learning.ElearningBE.storage.criteria.QuizQuestionCriteria;
import com.api.learning.ElearningBE.storage.entities.AnswerQuestion;
import com.api.learning.ElearningBE.storage.entities.Quiz;
import com.api.learning.ElearningBE.storage.entities.QuizQuestion;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class QuizQuestionServiceImpl implements QuizQuestionService{

    @Autowired
    private QuizQuestionRepository quizQuestionRepository;
    @Autowired
    private QuizQuestionMapper quizQuestionMapper;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private AnswerQuestionRepository answerQuestionRepository;
    @Autowired
    private AnswerQuestionMapper answerQuestionMapper;

    @Override
    public ApiMessageDto<ResponseListDto<List<QuizQuestionDto>>> list(QuizQuestionCriteria quizQuestionCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<QuizQuestionDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<QuizQuestionDto>> responseListDto = new ResponseListDto<>();
        Page<QuizQuestion> quizQuestions = quizQuestionRepository.findAll(quizQuestionCriteria.getSpecification(),pageable);
        List<Long> questionIds = quizQuestions.getContent().parallelStream().map(QuizQuestion::getId).collect(Collectors.toList());
        List<AnswerQuestion> answers = answerQuestionRepository.findAllByQuestionIdIn(questionIds);
        Map<Long, List<AnswerQuestion>> answersMap = answers.parallelStream().collect(Collectors.groupingByConcurrent(answer -> answer.getQuestion().getId()));

        List<QuizQuestionDto> quizQuestionDtoS = quizQuestionMapper.fromEntityToQuizQuestionDtoList(quizQuestions.getContent());
        quizQuestionDtoS.replaceAll(quizQuestionDto -> {
            List<AnswerQuestion> answerQuestion = answersMap.getOrDefault(quizQuestionDto.getId(), Collections.emptyList());
            quizQuestionDto.setAnswers(answerQuestionMapper.fromEntityToAnswerQuestionDtoList(answerQuestion));
            return quizQuestionDto;
        });

        responseListDto.setPageIndex(quizQuestions.getNumber());
        responseListDto.setContent(quizQuestionDtoS);
        responseListDto.setPageSize(quizQuestions.getSize());
        responseListDto.setTotalPages(quizQuestions.getTotalPages());
        responseListDto.setTotalElements(quizQuestions.getTotalElements());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Retrieve question list sucessfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<QuizQuestionAdminDto> retrieve(Long id) {
        ApiMessageDto<QuizQuestionAdminDto> apiMessageDto = new ApiMessageDto<>();
        QuizQuestion question = quizQuestionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Question with id %s not found", id)));
        QuizQuestionAdminDto quizQuestionAdminDto = quizQuestionMapper.fromEntityToQuizQuestionAdminDto(question);

        apiMessageDto.setData(quizQuestionAdminDto);
        apiMessageDto.setMessage("Retrieve question successfully");
        return apiMessageDto;
    }

    @Override
    @Transactional
    public ApiMessageDto<QuizQuestionDto> create(CreateQuizQuestionForm createQuizQuestionForm) {
        ApiMessageDto<QuizQuestionDto> apiMessageDto = new ApiMessageDto<>();
        Quiz quiz = quizRepository.findById(createQuizQuestionForm.getQuizId())
                .orElseThrow(() -> new NotFoundException(String.format("Quiz with id %s not found", createQuizQuestionForm.getQuizId())));
        QuizQuestion question = quizQuestionMapper.fromCreateQuizQuestionFormToEntity(createQuizQuestionForm);
        question.setQuiz(quiz);
        question.setScore(ELearningConstant.QUIZ_QUESTION_SCORE_DEFAULT);
        quizQuestionRepository.save(question);

        List<AnswerQuestionDto> answerQuestionDtoList = new ArrayList<>();
        if (createQuizQuestionForm.getAnswers() != null) {
            List<AnswerQuestion> answers = answerQuestionMapper.fromCreateAnswerQuestionFormForCreateQuestionToEntityList(createQuizQuestionForm.getAnswers());
            answers.replaceAll(answer -> {
                answer.setQuestion(question);
                return answer;
            });
            answerQuestionRepository.saveAll(answers);
            answerQuestionDtoList = answerQuestionMapper.fromEntityToAnswerQuestionDtoList(answers);
        }

        QuizQuestionDto quizQuestionDto = quizQuestionMapper.fromEntityToQuizQuestionDto(question);
        quizQuestionDto.setAnswers(answerQuestionDtoList);

        apiMessageDto.setData(quizQuestionDto);
        apiMessageDto.setMessage("Create question successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<QuizQuestionDto> update(UpdateQuizQuestionForm updateQuizQuestionForm) {
        ApiMessageDto<QuizQuestionDto> apiMessageDto = new ApiMessageDto<>();
        QuizQuestion question = quizQuestionRepository.findById(updateQuizQuestionForm.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Question with id %s not found", updateQuizQuestionForm.getId())));
        quizQuestionMapper.fromUpdateQuizQuestionFormToEntity(updateQuizQuestionForm,question);
        quizQuestionRepository.save(question);
        List<AnswerQuestionDto> answerQuestionDtoList = new ArrayList<>();
        if (updateQuizQuestionForm.getAnswers() != null) {
            List<Long> answerIds = updateQuizQuestionForm.getAnswers().parallelStream().map(UpdateAnswerQuestionForm::getId).collect(Collectors.toList());
            List<AnswerQuestion> answers = answerQuestionRepository.findAllByIdIn(answerIds);
            Map<Long, UpdateAnswerQuestionForm> updateAnswersMap = updateQuizQuestionForm.getAnswers().parallelStream().collect(Collectors.toMap(UpdateAnswerQuestionForm::getId, Function.identity()));
            for (AnswerQuestion answer : answers) {
                answerQuestionMapper.fromUpdateAnswerQuestionFormToEntity(updateAnswersMap.getOrDefault(answer.getId(), null), answer);
            }
            answerQuestionRepository.saveAll(answers);
            answerQuestionDtoList = answerQuestionMapper.fromEntityToAnswerQuestionDtoList(answers);
        }
        QuizQuestionDto quizQuestionDto = quizQuestionMapper.fromEntityToQuizQuestionDto(question);
        quizQuestionDto.setAnswers(answerQuestionDtoList);

        apiMessageDto.setData(quizQuestionDto);
        apiMessageDto.setMessage("Update question successfully");
        return apiMessageDto;
    }

    @Override
    @Transactional
    public ApiMessageDto<String> delete(Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        QuizQuestion question = quizQuestionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Question with id %s not found", id)));
        answerQuestionRepository.deleteAllByQuestionId(question.getId());
        quizQuestionRepository.delete(question);

        apiMessageDto.setMessage("Delete question successfully");
        return apiMessageDto;
    }
}
