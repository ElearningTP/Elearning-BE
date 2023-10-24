package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.form.account.CreateAccountForm;
import com.api.learning.ElearningBE.storage.entities.Account;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "email", target = "email")
    @Mapping(source = "fullName", target = "fullName")
    Account fromCreateAccountFormToEntity(CreateAccountForm createAccountForm);
}
