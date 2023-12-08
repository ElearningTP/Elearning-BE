package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.role.RoleAdminDto;
import com.api.learning.ElearningBE.dto.role.RoleDto;
import com.api.learning.ElearningBE.form.role.CreateRoleForm;
import com.api.learning.ElearningBE.form.role.UpdateRoleForm;
import com.api.learning.ElearningBE.storage.entities.Role;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {PermissionMapper.class})
public interface RoleMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "roleName", target = "name")
    @Mapping(source = "roleKind", target = "kind")
    @Mapping(source = "description", target = "description")
    Role fromCreateGroupFormToEntity(CreateRoleForm createRoleForm);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "roleName", target = "name")
    @Mapping(source = "description", target = "description")
    void fromUpdateGroupFormToEntity(UpdateRoleForm updateRoleForm, @MappingTarget Role role);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "permissions", target = "permissions", qualifiedByName = "fromEntityToPermissionDto")
    @Named("fromEntityToRoleDto")
    RoleAdminDto fromEntityToRoleDto(Role role);
    @IterableMapping(elementTargetType = RoleAdminDto.class, qualifiedByName = "fromEntityToRoleDto")
    List<RoleAdminDto> fromEntityToRoleDtoList(List<Role> roleList);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "description", target = "description")
    @Named("fromEntityToRoleDtoForMe")
    RoleDto fromEntityToRoleDtoForMe(Role role);
}
