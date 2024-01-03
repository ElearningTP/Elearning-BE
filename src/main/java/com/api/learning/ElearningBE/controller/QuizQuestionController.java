package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.quiz_question.QuizQuestionAdminDto;
import com.api.learning.ElearningBE.dto.quiz_question.QuizQuestionDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.quiz_question.CreateQuizQuestionForm;
import com.api.learning.ElearningBE.form.quiz_question.UpdateQuizQuestionForm;
import com.api.learning.ElearningBE.services.quiz_question.QuizQuestionService;
import com.api.learning.ElearningBE.storage.criteria.QuizQuestionCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/quiz-question")
public class QuizQuestionController {

    private final QuizQuestionService quizQuestionService;

    public QuizQuestionController(QuizQuestionService quizQuestionService) {
        this.quizQuestionService = quizQuestionService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('Q_QUESTION_L')")
    public ApiMessageDto<ResponseListDto<List<QuizQuestionDto>>> list(QuizQuestionCriteria quizQuestionCriteria, Pageable pageable){
        ApiMessageDto<ResponseListDto<List<QuizQuestionDto>>> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = quizQuestionService.list(quizQuestionCriteria, pageable);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @GetMapping("/retrieve/{id}")
    @PreAuthorize("hasRole('Q_QUESTION_V')")
    public ApiMessageDto<QuizQuestionAdminDto> retrieve(@PathVariable Long id){
        ApiMessageDto<QuizQuestionAdminDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = quizQuestionService.retrieve(id);
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
    @PreAuthorize("hasRole('Q_QUESTION_C')")
    public ApiMessageDto<QuizQuestionDto> create(@Valid @RequestBody CreateQuizQuestionForm createQuizQuestionForm){
        ApiMessageDto<QuizQuestionDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = quizQuestionService.create(createQuizQuestionForm);
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
    @PreAuthorize("hasRole('Q_QUESTION_U')")
    public ApiMessageDto<QuizQuestionDto> update(@Valid @RequestBody UpdateQuizQuestionForm updateQuizQuestionForm){
        ApiMessageDto<QuizQuestionDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = quizQuestionService.update(updateQuizQuestionForm);
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
    @PreAuthorize("hasRole('Q_QUESTION_D')")
    public ResponseEntity<ApiMessageDto<String>> delete(@PathVariable Long id){
        ResponseEntity<ApiMessageDto<String>> response;
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = quizQuestionService.delete(id);
            response = new ResponseEntity<>(apiMessageDto, HttpStatus.OK);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
            response = new ResponseEntity<>(apiMessageDto, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            response = new ResponseEntity<>(apiMessageDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
