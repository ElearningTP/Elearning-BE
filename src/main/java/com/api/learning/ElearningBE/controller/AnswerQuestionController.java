package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.answer_question.AnswerQuestionAdminDto;
import com.api.learning.ElearningBE.dto.answer_question.AnswerQuestionDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.answer_question.CreateAnswerQuestionForm;
import com.api.learning.ElearningBE.form.answer_question.UpdateAnswerQuestionForm;
import com.api.learning.ElearningBE.services.answer_question.AnswerQuestionService;
import com.api.learning.ElearningBE.storage.criteria.AnswerQuestionCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/answer-question")
public class AnswerQuestionController {

    private final AnswerQuestionService answerQuestionService;

    public AnswerQuestionController(AnswerQuestionService answerQuestionService) {
        this.answerQuestionService = answerQuestionService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ANSWER_L')")
    public ApiMessageDto<ResponseListDto<List<AnswerQuestionDto>>> list(AnswerQuestionCriteria answerQuestionCriteria, Pageable pageable){
        ApiMessageDto<ResponseListDto<List<AnswerQuestionDto>>> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = answerQuestionService.list(answerQuestionCriteria, pageable);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @GetMapping("/retrieve/{id}")
    @PreAuthorize("hasRole('ANSWER_V')")
    public ApiMessageDto<AnswerQuestionAdminDto> retrieve(@PathVariable Long id){
        ApiMessageDto<AnswerQuestionAdminDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = answerQuestionService.retrieve(id);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ANSWER_C')")
    public ApiMessageDto<AnswerQuestionDto> create(@Valid @RequestBody CreateAnswerQuestionForm createAnswerQuestionForm){
        ApiMessageDto<AnswerQuestionDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = answerQuestionService.create(createAnswerQuestionForm);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ANSWER_U')")
    public ApiMessageDto<AnswerQuestionDto> update(@Valid @RequestBody UpdateAnswerQuestionForm updateAnswerQuestionForm){
        ApiMessageDto<AnswerQuestionDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = answerQuestionService.update(updateAnswerQuestionForm);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ANSWER_D')")
    public ApiMessageDto<String> delete(@PathVariable Long id){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = answerQuestionService.delete(id);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }
}
