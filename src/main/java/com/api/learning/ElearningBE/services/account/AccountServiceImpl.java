package com.api.learning.ElearningBE.services.account;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.account.AccountDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.account.CreateAccountForm;
import com.api.learning.ElearningBE.mapper.AccountMapper;
import com.api.learning.ElearningBE.repositories.AccountRepository;
import com.api.learning.ElearningBE.repositories.NationRepository;
import com.api.learning.ElearningBE.repositories.RoleRepository;
import com.api.learning.ElearningBE.security.JwtUtils;
import com.api.learning.ElearningBE.storage.entities.Account;
import com.api.learning.ElearningBE.storage.entities.Nation;
import com.api.learning.ElearningBE.storage.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private NationRepository nationRepository;
    @Autowired
    private JwtUtils jwtUtils;

    private String extractToken(String token){
        if (!StringUtils.isEmpty(token) && token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return null;
    }
    @Override
    public ApiMessageDto<AccountDto> retrieveMe(String token) {
        ApiMessageDto<AccountDto> apiMessageDto = new ApiMessageDto<>();
        String jwt = extractToken(token);
        String email = jwtUtils.getEmailFromToken(jwt);
        Account account = accountRepository.findByEmail(email);
        if (account == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(String.format("Account with email %s not found", email));
            return apiMessageDto;
        }
        AccountDto accountDto = accountMapper.fromEntityToAccountDtoForMe(account);

        apiMessageDto.setData(accountDto);
        apiMessageDto.setMessage("Retrieve info successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> create(CreateAccountForm createAccountForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Boolean emailExisted = accountRepository.existsAccountByEmail(createAccountForm.getEmail().trim());
        if (emailExisted){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(String.format("Email %s is existed", createAccountForm.getEmail()));
            return apiMessageDto;
        }
        Nation nation = nationRepository.findById(createAccountForm.getNationId())
                .orElseThrow(() -> new NotFoundException(String.format("Nation with id %s not found", createAccountForm.getNationId())));
        Role role = roleRepository.findById(createAccountForm.getRoleId())
                .orElseThrow(() -> new NotFoundException(String.format("Role with id %s not found", createAccountForm.getRoleId())));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(createAccountForm.getPassword());

        Account account = accountMapper.fromCreateAccountFormToEntity(createAccountForm);
        account.setNation(nation);
        account.setRole(role);
        account.setPassword(hash);
        accountRepository.save(account);

        apiMessageDto.setMessage("Create account successfully.");
        return apiMessageDto;

    }

}
