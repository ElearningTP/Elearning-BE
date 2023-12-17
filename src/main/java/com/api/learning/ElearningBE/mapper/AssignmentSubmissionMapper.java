package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.assignment_submission.AssignmentSubmissionAdminDto;
import com.api.learning.ElearningBE.dto.assignment_submission.AssignmentSubmissionDto;
import com.api.learning.ElearningBE.form.assignment_submission.CreateAssignmentSubmissionForm;
import com.api.learning.ElearningBE.form.assignment_submission.UpdateAssignmentSubmissionForm;
import com.api.learning.ElearningBE.storage.entities.AssignmentSubmission;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AssignmentMapper.class, AccountMapper.class})
public interface AssignmentSubmissionMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "textSubmission", target = "textSubmission")
    @Mapping(source = "fileSubmissionUrl", target = "fileSubmissionUrl")
    @Mapping(source = "linkSubmission", target = "linkSubmission")
    AssignmentSubmission fromCreateAssignmentSubmissionFormToEntity(CreateAssignmentSubmissionForm createAssignmentSubmissionForm);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "textSubmission", target = "textSubmission")
    @Mapping(source = "fileSubmissionUrl", target = "fileSubmissionUrl")
    @Mapping(source = "linkSubmission", target = "linkSubmission")
    void fromUpdateAssignmentSubmissionFormToEntity(UpdateAssignmentSubmissionForm updateAssignmentSubmissionForm, @MappingTarget AssignmentSubmission assignmentSubmission);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "textSubmission", target = "textSubmission")
    @Mapping(source = "fileSubmissionUrl", target = "fileSubmissionUrl")
    @Mapping(source = "linkSubmission", target = "linkSubmission")
    @Mapping(source = "status",target = "status")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "assignment", target = "assignmentInfo", qualifiedByName = "fromEntityToAssignmentDto")
    @Mapping(source = "student", target = "studentInfo", qualifiedByName = "fromEntityToAccountDtoAutoComplete")
    @Named("fromEntityToAssignmentSubmissionAdminDto")
    AssignmentSubmissionAdminDto fromEntityToAssignmentSubmissionAdminDto(AssignmentSubmission assignmentSubmission);
    @IterableMapping(elementTargetType = AssignmentSubmissionAdminDto.class, qualifiedByName = "fromEntityToAssignmentSubmissionAdminDto")
    List<AssignmentSubmissionAdminDto> fromEntityToAssignmentSubmissionAdminDtoList(List<AssignmentSubmission> assignmentSubmissions);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "assignment", target = "assignmentInfo", qualifiedByName = "fromEntityToAssignmentDto")
    @Named("fromEntityToAssignmentSubmissionDto")
    AssignmentSubmissionDto fromEntityToAssignmentSubmissionDto(AssignmentSubmission assignmentSubmission);
    @IterableMapping(elementTargetType = AssignmentSubmissionDto.class, qualifiedByName = "fromEntityToAssignmentSubmissionDto")
    List<AssignmentSubmissionDto> fromEntityToAssignmentSubmissionDtoList(List<AssignmentSubmission> assignmentSubmissions);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "score", target = "score")
    @Named("fromEntityToAssignmentSubmissionDtoForMySchedule")
    AssignmentSubmissionDto fromEntityToAssignmentSubmissionDtoForMySchedule(AssignmentSubmission assignmentSubmission);
    @IterableMapping(elementTargetType = AssignmentSubmissionDto.class, qualifiedByName = "fromEntityToAssignmentSubmissionDtoForMySchedule")
    List<AssignmentSubmissionDto> fromEntityToAssignmentSubmissionDtoForMyScheduleList(List<AssignmentSubmission> assignmentSubmissions);
}
