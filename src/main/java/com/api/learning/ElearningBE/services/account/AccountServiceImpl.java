package com.api.learning.ElearningBE.services.account;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.account.CreateAccountForm;
import com.api.learning.ElearningBE.mapper.AccountMapper;
import com.api.learning.ElearningBE.repositories.AccountRepository;
import com.api.learning.ElearningBE.repositories.GroupRepository;
import com.api.learning.ElearningBE.storage.entities.Account;
import com.api.learning.ElearningBE.storage.entities.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

@Service(value = "accountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public ApiMessageDto<String> create(CreateAccountForm createAccountForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Boolean emailExisted = accountRepository.existsAccountByEmail(createAccountForm.getEmail().trim());
        if (emailExisted){
            apiMessageDto.setMessage(String.format("Email %s is existed", createAccountForm.getEmail()));
            return apiMessageDto;
        }
        Group group = groupRepository.findById(createAccountForm.getGroupId()).orElse(null);
        if (group == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(String.format("Not found group with id %s", createAccountForm.getGroupId()));
            return apiMessageDto;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(createAccountForm.getPassword());
        Account account = accountMapper.fromCreateAccountFormToEntity(createAccountForm);
        account.setGroup(group);
        account.setPassword(hash);
        accountRepository.save(account);

        apiMessageDto.setMessage("Create account successfully.");
        return apiMessageDto;

    }

}
