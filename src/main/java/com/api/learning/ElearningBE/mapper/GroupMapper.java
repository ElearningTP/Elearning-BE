package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.form.group.CreateGroupForm;
import com.api.learning.ElearningBE.storage.entities.Group;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GroupMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "groupName", target = "name")
    @Mapping(source = "groupKind", target = "kind")
    @Mapping(source = "description", target = "description")
    Group fromCreateGroupFormToEntity(CreateGroupForm createGroupForm);
}
