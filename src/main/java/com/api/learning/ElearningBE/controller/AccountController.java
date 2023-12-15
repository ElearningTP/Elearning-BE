package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.account.AccountDto;
import com.api.learning.ElearningBE.dto.account.StudentScheduleDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.account.CreateAccountForm;
import com.api.learning.ElearningBE.services.account.AccountService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/account")
@Slf4j
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/all-student")
    public ApiMessageDto<ResponseListDto<List<AccountDto>>> getAllStudentsByCourse(@RequestParam Long courseId, Pageable pageable){
        ApiMessageDto<ResponseListDto<List<AccountDto>>> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = accountService.getAllStudentByCourse(courseId,pageable);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            return apiMessageDto;
        }
        return apiMessageDto;
    }

    @GetMapping("/member")
    public ApiMessageDto<ResponseListDto<List<AccountDto>>> memberTheSameOfCourse(Pageable pageable){
        ApiMessageDto<ResponseListDto<List<AccountDto>>> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = accountService.memberTheSameCourse(pageable);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            return apiMessageDto;
        }
        return apiMessageDto;
    }

    @GetMapping("/me")
    public ApiMessageDto<AccountDto> retrieveMe(@RequestHeader(name = "Authorization") String token){
        ApiMessageDto<AccountDto> apiMessageDto = new ApiMessageDto<>();
        try{
            apiMessageDto = accountService.retrieveMe(token);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            return apiMessageDto;
        }
        return apiMessageDto;
    }

    @GetMapping("/my-schedule")
    public ApiMessageDto<StudentScheduleDto> mySchedule(){
        ApiMessageDto<StudentScheduleDto> apiMessageDto = new ApiMessageDto<>();
        try{
            apiMessageDto = accountService.mySchedule();
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            return apiMessageDto;
        }
        return apiMessageDto;
    }

    @PostMapping(value = "/create")
//    @PreAuthorize("hasRole('AC_C')")
    public ApiMessageDto<String> create(@Valid @RequestBody CreateAccountForm createAccountForm){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = accountService.create(createAccountForm);
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
