package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.form.role.CreateRoleForm;
import com.api.learning.ElearningBE.form.role.UpdateRoleForm;
import com.api.learning.ElearningBE.storage.entities.Role;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
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
}
