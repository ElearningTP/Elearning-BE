package com.api.learning.ElearningBE.services.account;

import com.api.learning.ElearningBE.dto.ApiMessageDto;

import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.account.AccountAdminDto;
import com.api.learning.ElearningBE.dto.account.AccountDto;
import com.api.learning.ElearningBE.dto.account.StudentScheduleDto;
import com.api.learning.ElearningBE.form.account.CreateAccountForm;
import com.api.learning.ElearningBE.form.account.UpdateAccountForm;
import com.api.learning.ElearningBE.storage.criteria.AccountCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
    ApiMessageDto<ResponseListDto<List<AccountAdminDto>>> list(AccountCriteria accountCriteria, Pageable pageable);
    ApiMessageDto<ResponseListDto<List<AccountDto>>> autoComplete(AccountCriteria accountCriteria, Pageable pageable);
    ApiMessageDto<ResponseListDto<List<AccountDto>>> getAllStudentByCourse(Long courseId, Pageable pageable);
    ApiMessageDto<ResponseListDto<List<AccountDto>>> memberTheSameCourse(Pageable pageable);
    ApiMessageDto<AccountDto> retrieveMe(String token);
    ApiMessageDto<StudentScheduleDto> mySchedule();
    ApiMessageDto<AccountDto> create(CreateAccountForm createAccountForm);
    ApiMessageDto<AccountDto> update(UpdateAccountForm updateAccountForm);
    ApiMessageDto<String> delete(Long id);
}
