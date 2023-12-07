package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.quiz_submission.QuizSubmissionDto;
import com.api.learning.ElearningBE.dto.quiz_submission.ReviewQuizSubmissionDto;
import com.api.learning.ElearningBE.exceptions.InvalidException;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.quiz_submission.CreateQuizSubmissionForm;
import com.api.learning.ElearningBE.services.quiz_submission.QuizSubmissionService;
import com.api.learning.ElearningBE.storage.criteria.QuizSubmissionCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/quiz-submission")
public class QuizSubmissionController {


    private final QuizSubmissionService quizSubmissionService;

    public QuizSubmissionController(QuizSubmissionService quizSubmissionService) {
        this.quizSubmissionService = quizSubmissionService;
    }

    @GetMapping("/list")
    public ApiMessageDto<ResponseListDto<List<QuizSubmissionDto>>> list(QuizSubmissionCriteria quizSubmissionCriteria, Pageable pageable){
        ApiMessageDto<ResponseListDto<List<QuizSubmissionDto>>> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = quizSubmissionService.list(quizSubmissionCriteria, pageable);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @GetMapping("/review/{id}")
    public ApiMessageDto<ReviewQuizSubmissionDto> review(@PathVariable Long id){
        ApiMessageDto<ReviewQuizSubmissionDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = quizSubmissionService.review(id);
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

    @PostMapping("/submit")
    public ApiMessageDto<QuizSubmissionDto> submit(@Valid @RequestBody CreateQuizSubmissionForm createQuizSubmissionForm){
        ApiMessageDto<QuizSubmissionDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = quizSubmissionService.submit(createQuizSubmissionForm);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }catch (InvalidException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.BAD_REQUEST.toString());
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }
}
