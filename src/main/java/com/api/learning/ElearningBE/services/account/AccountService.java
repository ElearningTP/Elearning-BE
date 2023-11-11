package com.api.learning.ElearningBE.services.account;

import com.api.learning.ElearningBE.dto.ApiMessageDto;

import com.api.learning.ElearningBE.dto.account.AccountDto;
import com.api.learning.ElearningBE.form.account.CreateAccountForm;

public interface AccountService {
    ApiMessageDto<AccountDto> retrieveMe(String token);
    ApiMessageDto<String> create(CreateAccountForm createAccountForm);
}
