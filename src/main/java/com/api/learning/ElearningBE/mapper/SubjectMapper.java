package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.subject.SubjectAdminDto;
import com.api.learning.ElearningBE.form.subject.CreateSubjectForm;
import com.api.learning.ElearningBE.form.subject.UpdateSubjectForm;
import com.api.learning.ElearningBE.storage.entities.Subject;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SubjectMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "subjectName", target = "name")
    @Mapping(source = "subjectCode", target = "code")
    @Mapping(source = "description", target = "description")
    Subject fromCreateSubjectFormToEntity(CreateSubjectForm createSubjectForm);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "subjectName", target = "name")
    @Mapping(source = "subjectCode", target = "code")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "status", target = "status")
    void fromUpdateSubjectFormToEntity(UpdateSubjectForm updateSubjectForm, @MappingTarget Subject subject);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "subjectName")
    @Mapping(source = "code", target = "subjectCode")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Named("fromEntityToSubjectAdminDtoForGet")
    SubjectAdminDto fromEntityToSubjectAdminDtoForGet(Subject subject);
    @IterableMapping(elementTargetType = SubjectAdminDto.class, qualifiedByName = "fromEntityToSubjectAdminDtoForGet")
    List<SubjectAdminDto> fromEntityToSubjectAdminDtoList(List<Subject> subjects);
}
