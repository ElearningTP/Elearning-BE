package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.form.account.CreateAccountForm;
import com.api.learning.ElearningBE.mapper.AccountMapper;
import com.api.learning.ElearningBE.repositories.AccountRepository;
import com.api.learning.ElearningBE.services.account.AccountService;
import com.api.learning.ElearningBE.storage.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountMapper accountMapper;

    @PostMapping(value = "/create")
    public ApiMessageDto<String> create(@Valid @RequestBody CreateAccountForm createAccountForm, BindingResult bindingResult){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Boolean emailExisted = accountRepository.existsAccountByEmail(createAccountForm.getEmail());
        if (emailExisted){
            apiMessageDto.setMessage(String.format("Email %s is existed", createAccountForm.getEmail()));
            return apiMessageDto;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(createAccountForm.getPassword());
        Account account = accountMapper.fromCreateAccountFormToEntity(createAccountForm);
        account.setPassword(hash);
        accountRepository.save(account);

        apiMessageDto.setMessage("Create account successfully.");
        return apiMessageDto;
//        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
//        try {
//            apiMessageDto = accountService.create(createAccountForm, bindingResult);
//        }catch (Exception e){
//            apiMessageDto.setResult(false);
//            apiMessageDto.setMessage(e.getMessage());
//            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
//        }
//        return apiMessageDto;
    }
}
