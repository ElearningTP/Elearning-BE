package com.api.learning.ElearningBE.services.quiz_question;

import com.api.learning.ElearningBE.constant.ELearningConstant;
import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.quiz_question.QuizQuestionAdminDto;
import com.api.learning.ElearningBE.dto.quiz_question.QuizQuestionDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.quiz_question.CreateQuizQuestionForm;
import com.api.learning.ElearningBE.form.quiz_question.UpdateQuizQuestionForm;
import com.api.learning.ElearningBE.mapper.QuizQuestionMapper;
import com.api.learning.ElearningBE.repositories.AnswerQuestionRepository;
import com.api.learning.ElearningBE.repositories.QuizQuestionRepository;
import com.api.learning.ElearningBE.repositories.QuizRepository;
import com.api.learning.ElearningBE.storage.criteria.QuizQuestionCriteria;
import com.api.learning.ElearningBE.storage.entities.Quiz;
import com.api.learning.ElearningBE.storage.entities.QuizQuestion;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public ApiMessageDto<ResponseListDto<List<QuizQuestionDto>>> list(QuizQuestionCriteria quizQuestionCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<QuizQuestionDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<QuizQuestionDto>> responseListDto = new ResponseListDto<>();
        Page<QuizQuestion> quizQuestions = quizQuestionRepository.findAll(quizQuestionCriteria.getSpecification(),pageable);
        List<QuizQuestionDto> quizQuestionDtoS = quizQuestionMapper.fromEntityToQuizQuestionDtoList(quizQuestions.getContent());

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
    public ApiMessageDto<QuizQuestionDto> create(CreateQuizQuestionForm createQuizQuestionForm) {
        ApiMessageDto<QuizQuestionDto> apiMessageDto = new ApiMessageDto<>();
        Quiz quiz = quizRepository.findById(createQuizQuestionForm.getQuizId())
                .orElseThrow(() -> new NotFoundException(String.format("Quiz with id %s not found", createQuizQuestionForm.getQuizId())));
        QuizQuestion question = quizQuestionMapper.fromCreateQuizQuestionFormToEntity(createQuizQuestionForm);
        question.setQuiz(quiz);
        question.setScore(ELearningConstant.QUIZ_QUESTION_SCORE_DEFAULT);
        quizQuestionRepository.save(question);
        QuizQuestionDto quizQuestionDto = quizQuestionMapper.fromEntityToQuizQuestionDto(question);

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
        QuizQuestionDto quizQuestionDto = quizQuestionMapper.fromEntityToQuizQuestionDto(question);

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
