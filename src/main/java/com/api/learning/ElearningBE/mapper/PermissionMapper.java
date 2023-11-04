package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.permission.PermissionDto;
import com.api.learning.ElearningBE.form.permission.CreatePermissionForm;
import com.api.learning.ElearningBE.storage.entities.Permission;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PermissionMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "permissionName", target = "name")
    @Mapping(source = "permissionAction", target = "action")
    @Mapping(source = "showMenu", target = "showMenu")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "roleName", target = "roleName")
    @Mapping(source = "permissionCode", target = "permissionCode")
    Permission fromCreatePermissionFormToEntity(CreatePermissionForm createPermissionForm);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "action", target = "action")
    @Mapping(source = "showMenu", target = "showMenu")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "roleName", target = "roleName")
    @Mapping(source = "permissionCode", target = "permissionCode")
    @Named("fromEntityToPermissionDto")
    PermissionDto fromEntityToPermissionDto(Permission permission);
}
