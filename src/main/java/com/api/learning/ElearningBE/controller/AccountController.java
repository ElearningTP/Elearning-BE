package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.account.AccountAdminDto;
import com.api.learning.ElearningBE.dto.account.AccountDto;
import com.api.learning.ElearningBE.dto.account.StudentScheduleDto;
import com.api.learning.ElearningBE.exceptions.InvalidException;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.account.CreateAccountForm;
import com.api.learning.ElearningBE.form.account.UpdateAccountForm;
import com.api.learning.ElearningBE.services.account.AccountService;
import com.api.learning.ElearningBE.storage.criteria.AccountCriteria;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/list")
    public ApiMessageDto<ResponseListDto<List<AccountAdminDto>>> list(AccountCriteria accountCriteria, Pageable pageable){
        ApiMessageDto<ResponseListDto<List<AccountAdminDto>>> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = accountService.list(accountCriteria,pageable);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            return apiMessageDto;
        }
        return apiMessageDto;
    }

    @GetMapping("/auto-complete")
    public ApiMessageDto<ResponseListDto<List<AccountDto>>> autoComplete(AccountCriteria accountCriteria, Pageable pageable){
        ApiMessageDto<ResponseListDto<List<AccountDto>>> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = accountService.autoComplete(accountCriteria,pageable);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            return apiMessageDto;
        }
        return apiMessageDto;
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
    public ApiMessageDto<AccountDto> create(@Valid @RequestBody CreateAccountForm createAccountForm){
        ApiMessageDto<AccountDto> apiMessageDto = new ApiMessageDto<>();
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

    @PutMapping(value = "/update")
//    @PreAuthorize("hasRole('AC_C')")
    public ApiMessageDto<AccountDto> update(@Valid @RequestBody UpdateAccountForm updateAccountForm){
        ApiMessageDto<AccountDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = accountService.update(updateAccountForm);
        }catch (InvalidException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.BAD_REQUEST.toString());
        }
        catch (NotFoundException e){
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

    @DeleteMapping(value = "/delete/{id}")
//    @PreAuthorize("hasRole('AC_C')")
    public ApiMessageDto<String> create(@PathVariable Long id){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = accountService.delete(id);
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
