package com.api.learning.ElearningBE.services.account;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.form.account.CreateAccountForm;
import org.springframework.validation.BindingResult;

public interface AccountService {
    ApiMessageDto<String> create(CreateAccountForm createAccountForm);
}
