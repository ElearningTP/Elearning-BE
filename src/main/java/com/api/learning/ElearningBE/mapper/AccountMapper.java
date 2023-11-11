package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.account.AccountDto;
import com.api.learning.ElearningBE.form.account.CreateAccountForm;
import com.api.learning.ElearningBE.storage.entities.Account;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {NationMapper.class, RoleMapper.class})
public interface AccountMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "avatarPath", target = "avatarPath")
    Account fromCreateAccountFormToEntity(CreateAccountForm createAccountForm);

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

}
