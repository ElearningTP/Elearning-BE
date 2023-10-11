package com.api.learning.ElearningBE.mapper;

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
    @Mapping(source = "nameGroup", target = "nameGroup")
    @Mapping(source = "permissionCode", target = "permissionCode")
    Permission fromCreatePermissionFormToEntity(CreatePermissionForm createPermissionForm);
}
