package com.api.learning.ElearningBE.services.answer_question;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.answer_question.AnswerQuestionAdminDto;
import com.api.learning.ElearningBE.dto.answer_question.AnswerQuestionDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.answer_question.CreateAnswerQuestionForm;
import com.api.learning.ElearningBE.form.answer_question.UpdateAnswerQuestionForm;
import com.api.learning.ElearningBE.mapper.AnswerQuestionMapper;
import com.api.learning.ElearningBE.repositories.AnswerQuestionRepository;
import com.api.learning.ElearningBE.repositories.QuizQuestionRepository;
import com.api.learning.ElearningBE.storage.criteria.AnswerQuestionCriteria;
import com.api.learning.ElearningBE.storage.entities.AnswerQuestion;
import com.api.learning.ElearningBE.storage.entities.QuizQuestion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerQuestionServiceImpl implements AnswerQuestionService{

    @Autowired
    private AnswerQuestionRepository answerQuestionRepository;
    @Autowired
    private AnswerQuestionMapper answerQuestionMapper;
    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    @Override
    public ApiMessageDto<ResponseListDto<List<AnswerQuestionDto>>> list(AnswerQuestionCriteria answerQuestionCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<AnswerQuestionDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<AnswerQuestionDto>> responseListDto = new ResponseListDto<>();
        Page<AnswerQuestion> answerQuestions = answerQuestionRepository.findAll(answerQuestionCriteria.getSpecification(),pageable);
        List<AnswerQuestionDto> answerQuestionDtoS = answerQuestionMapper.fromEntityToAnswerQuestionDtoList(answerQuestions.getContent());

        responseListDto.setTotalElements(answerQuestions.getTotalElements());
        responseListDto.setPageSize(answerQuestions.getSize());
        responseListDto.setContent(answerQuestionDtoS);
        responseListDto.setTotalPages(answerQuestions.getTotalPages());
        responseListDto.setPageIndex(answerQuestions.getNumber());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Retrieve answer list successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<AnswerQuestionAdminDto> retrieve(Long id) {
        ApiMessageDto<AnswerQuestionAdminDto> apiMessageDto = new ApiMessageDto<>();
        AnswerQuestion answerQuestion = answerQuestionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Answer with id %s not found", id)));
        AnswerQuestionAdminDto answerQuestionAdminDto = answerQuestionMapper.fromEntityToAnswerQuestionAdminDto(answerQuestion);

        apiMessageDto.setData(answerQuestionAdminDto);
        apiMessageDto.setMessage("Retrieve answer successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<AnswerQuestionDto> create(CreateAnswerQuestionForm createAnswerQuestionForm) {
        ApiMessageDto<AnswerQuestionDto> apiMessageDto = new ApiMessageDto<>();
        QuizQuestion question = quizQuestionRepository.findById(createAnswerQuestionForm.getQuestionId())
                .orElseThrow(() -> new NotFoundException(String.format("Question with id %s not found", createAnswerQuestionForm.getQuestionId())));
        AnswerQuestion answerQuestion = answerQuestionMapper.fromCreateAnswerQuestionFormToEntity(createAnswerQuestionForm);
        answerQuestion.setQuestion(question);
        answerQuestionRepository.save(answerQuestion);
        AnswerQuestionDto answerQuestionDto = answerQuestionMapper.fromEntityToAnswerQuestionDto(answerQuestion);

        apiMessageDto.setData(answerQuestionDto);
        apiMessageDto.setMessage("Create answer successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<AnswerQuestionDto> update(UpdateAnswerQuestionForm updateAnswerQuestionForm) {
        ApiMessageDto<AnswerQuestionDto> apiMessageDto = new ApiMessageDto<>();
        AnswerQuestion answerQuestion = answerQuestionRepository.findById(updateAnswerQuestionForm.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Answer with id %s not found", updateAnswerQuestionForm.getId())));
        answerQuestionMapper.fromUpdateAnswerQuestionFormToEntity(updateAnswerQuestionForm,answerQuestion);
        answerQuestionRepository.save(answerQuestion);
        AnswerQuestionDto answerQuestionDto = answerQuestionMapper.fromEntityToAnswerQuestionDto(answerQuestion);

        apiMessageDto.setData(answerQuestionDto);
        apiMessageDto.setMessage("Update answer successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> delete(Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        AnswerQuestion answerQuestion = answerQuestionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Answer with id %s not found", id)));
        answerQuestionRepository.delete(answerQuestion);

        apiMessageDto.setMessage("Delete answer successfully");
        return apiMessageDto;
    }
}
