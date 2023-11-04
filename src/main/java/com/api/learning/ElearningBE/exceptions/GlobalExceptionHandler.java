package com.api.learning.ElearningBE.exceptions;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.form.InvalidArgumentForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        List<InvalidArgumentForm> invalidArgumentForms = new ArrayList<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        invalidArgumentForms = fieldErrors
                .stream()
                .map(fieldError -> {
                    String field = fieldError.getField();
                    String message = fieldError.getDefaultMessage();
                    return new InvalidArgumentForm(field,message);
                })
                .collect(Collectors.toList());
        ApiMessageDto<List<InvalidArgumentForm>> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setResult(false);
        apiMessageDto.setCode("ERROR");
        apiMessageDto.setData(invalidArgumentForms);
        apiMessageDto.setMessage("Invalid form");
        return new ResponseEntity<>(apiMessageDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiMessageDto<String>> handleAccessDeniedException(AccessDeniedException ex){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setResult(false);
        apiMessageDto.setMessage("[Ex] "+ex.getMessage());
        return new ResponseEntity<>(apiMessageDto, HttpStatus.FORBIDDEN);
    }
}
