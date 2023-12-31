package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.account.AccountAdminDto;
import com.api.learning.ElearningBE.dto.account.AccountDto;
import com.api.learning.ElearningBE.form.account.CreateAccountForm;
import com.api.learning.ElearningBE.form.account.UpdateAccountForm;
import com.api.learning.ElearningBE.storage.entities.Account;
import org.mapstruct.*;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {NationMapper.class, RoleMapper.class})
public interface AccountMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "avatarPath", target = "avatarPath")
    Account fromCreateAccountFormToEntity(CreateAccountForm createAccountForm);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "avatarPath", target = "avatarPath")
    void fromUpdateAccountFormToEntity(UpdateAccountForm updateAccountForm, @MappingTarget Account account);
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "avatarPath", target = "avatarPath")
    @Mapping(source = "isSuperAdmin", target = "isSuperAdmin")
    @Mapping(source = "nation", target = "nationInfo", qualifiedByName = "fromEntityToNationDto")
    @Mapping(source = "role", target = "roleInfo", qualifiedByName = "fromEntityToRoleDtoForMe")
    AccountDto fromEntityToAccountDtoForMe(Account account);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "avatarPath", target = "avatarPath")
    @Named("fromEntityToAccountDtoAutoComplete")
    AccountDto fromEntityToAccountDtoAutoComplete(Account account);
    @IterableMapping(elementTargetType = AccountDto.class, qualifiedByName = "fromEntityToAccountDtoAutoComplete")
    List<AccountDto> fromEntityToAccountDtoAutoCompleteList(List<Account> accounts);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "avatarPath", target = "avatarPath")
    @Mapping(source = "isSuperAdmin", target = "isSuperAdmin")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "nation", target = "nationInfo", qualifiedByName = "fromEntityToNationDto")
    @Mapping(source = "role", target = "roleInfo", qualifiedByName = "fromEntityToRoleDtoForMe")
    @Named("fromEntityToAccountAdminDto")
    AccountAdminDto fromEntityToAccountAdminDto(Account account);
    @IterableMapping(elementTargetType = AccountAdminDto.class, qualifiedByName = "fromEntityToAccountAdminDto")
    List<AccountAdminDto> fromEntityToAccountAdminDtoList(List<Account> accounts);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "avatarPath", target = "avatarPath")
    @Mapping(source = "nation", target = "nationInfo", qualifiedByName = "fromEntityToNationDto")
    @Named("fromEntityToAccountDtForTheSameOfCourse")
    AccountDto fromEntityToAccountDtForTheSameOfCourse(Account account);
    @IterableMapping(elementTargetType = AccountDto.class, qualifiedByName = "fromEntityToAccountDtForTheSameOfCourse")
    List<AccountDto> fromEntityToAccountDtForTheSameOfCourseList(List<Account> accountList);
}
