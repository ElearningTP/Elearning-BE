package com.api.learning.ElearningBE.mapper;


import com.api.learning.ElearningBE.dto.assignment.AssignmentAdminDto;
import com.api.learning.ElearningBE.dto.assignment.AssignmentDto;
import com.api.learning.ElearningBE.form.assignment.CreateAssignmentForm;
import com.api.learning.ElearningBE.form.assignment.UpdateAssignmentForm;
import com.api.learning.ElearningBE.storage.entities.Assignment;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ModulesMapper.class})
public interface AssignmentMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "assignmentTitle", target = "title")
    @Mapping(source = "assignmentContent", target = "content")
    @Mapping(source = "assignmentType", target = "assignmentType")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "urlDocument", target = "urlDocument")
    Assignment fromCreateAssignmentFormToEntity(CreateAssignmentForm createAssignmentForm);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "assignmentTitle", target = "title")
    @Mapping(source = "assignmentContent", target = "content")
    @Mapping(source = "assignmentType", target = "assignmentType")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "urlDocument", target = "urlDocument")
    void fromUpdateAssignmentFormToEntity(UpdateAssignmentForm updateAssignmentForm, @MappingTarget Assignment assignment);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "title",target = "assignmentTitle")
    @Mapping(source = "content", target = "assignmentContent")
    @Mapping(source = "assignmentType", target = "assignmentType")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "urlDocument", target = "urlDocument")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "modules", target = "modulesInfo", qualifiedByName = "fromEntityToModulesDto")
    AssignmentAdminDto fromEntityToAssignmentAdminDto(Assignment assignment);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "title",target = "assignmentTitle")
    @Mapping(source = "content", target = "assignmentContent")
    @Mapping(source = "assignmentType", target = "assignmentType")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "urlDocument", target = "urlDocument")
    @Mapping(source = "modules", target = "modulesInfo", qualifiedByName = "fromEntityToModulesDto")
    @Named("fromEntityToAssignmentDto")
    AssignmentDto fromEntityToAssignmentDto(Assignment assignment);

    @IterableMapping(elementTargetType = AssignmentDto.class, qualifiedByName = "fromEntityToAssignmentDto")
    List<AssignmentDto> fromEntityToAssignmentDtoList(List<Assignment> assignments);
}
