package com.api.learning.ElearningBE.services.account;

import com.api.learning.ElearningBE.dto.ApiMessageDto;

import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.account.AccountDto;
import com.api.learning.ElearningBE.dto.account.StudentScheduleDto;
import com.api.learning.ElearningBE.form.account.CreateAccountForm;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
    ApiMessageDto<ResponseListDto<List<AccountDto>>> memberTheSameCourse(Pageable pageable);
    ApiMessageDto<AccountDto> retrieveMe(String token);
    ApiMessageDto<StudentScheduleDto> mySchedule();
    ApiMessageDto<String> create(CreateAccountForm createAccountForm);
}
