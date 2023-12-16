package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.quiz.QuizAdminDto;
import com.api.learning.ElearningBE.dto.quiz.QuizDto;
import com.api.learning.ElearningBE.dto.quiz.StartQuizDto;
import com.api.learning.ElearningBE.exceptions.InvalidException;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.quiz.CreateQuizForm;
import com.api.learning.ElearningBE.form.quiz.UpdateQuizForm;
import com.api.learning.ElearningBE.services.quiz.QuizService;
import com.api.learning.ElearningBE.storage.criteria.QuizCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/quiz")
@Validated
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }


    @GetMapping("/start")
    @PreAuthorize("hasRole('QUIZ_START')")
    public ApiMessageDto<StartQuizDto> start(@RequestParam("id") Long id,
                                             @RequestParam("courseId") Long courseId){
        ApiMessageDto<StartQuizDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = quizService.start(id, courseId);
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

    @GetMapping("/list")
    @PreAuthorize("hasRole('QUIZ_L')")
    public ApiMessageDto<ResponseListDto<List<QuizDto>>> list(QuizCriteria quizCriteria, Pageable pageable){
        ApiMessageDto<ResponseListDto<List<QuizDto>>> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = quizService.list(quizCriteria,pageable);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @GetMapping("/retrieve/{id}")
    @PreAuthorize("hasRole('QUIZ_V')")
    public ApiMessageDto<QuizAdminDto> retrieve(@PathVariable Long id,
                                                @RequestParam(required = false) Long courseId){
        ApiMessageDto<QuizAdminDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = quizService.retrieve(id, courseId);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }
        catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('QUIZ_C')")
    public ApiMessageDto<QuizDto> create(@Valid @RequestBody CreateQuizForm createQuizForm){
        ApiMessageDto<QuizDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = quizService.create(createQuizForm);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }
        catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('QUIZ_U')")
    public ApiMessageDto<QuizDto> update(@Valid @RequestBody UpdateQuizForm updateQuizForm){
        ApiMessageDto<QuizDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = quizService.update(updateQuizForm);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }
        catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('QUIZ_D')")
    public ResponseEntity<ApiMessageDto<String>> delete(@PathVariable Long id){
        ResponseEntity<ApiMessageDto<String>> response;
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = quizService.delete(id);
            response = new ResponseEntity<>(apiMessageDto, HttpStatus.OK);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
            response = new ResponseEntity<>(apiMessageDto, HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            response = new ResponseEntity<>(apiMessageDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
