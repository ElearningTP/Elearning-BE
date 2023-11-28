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
        uses = {AssignmentMapper.class})
public interface AssignmentSubmissionMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "submissionDate", target = "submissionDate")
    @Mapping(source = "textSubmission", target = "textSubmission")
    @Mapping(source = "fileSubmissionUrl", target = "fileSubmissionUrl")
    AssignmentSubmission fromCreateAssignmentSubmissionFormToEntity(CreateAssignmentSubmissionForm createAssignmentSubmissionForm);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "submissionDate", target = "submissionDate")
    @Mapping(source = "textSubmission", target = "textSubmission")
    @Mapping(source = "fileSubmissionUrl", target = "fileSubmissionUrl")
    void fromUpdateAssignmentSubmissionFormToEntity(UpdateAssignmentSubmissionForm updateAssignmentSubmissionForm, @MappingTarget AssignmentSubmission assignmentSubmission);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "submissionDate", target = "submissionDate")
    @Mapping(source = "textSubmission", target = "textSubmission")
    @Mapping(source = "fileSubmissionUrl", target = "fileSubmissionUrl")
    @Mapping(source = "status",target = "status")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Named("fromEntityToAssignmentSubmissionAdminDto")
    AssignmentSubmissionAdminDto fromEntityToAssignmentSubmissionAdminDto(AssignmentSubmission assignmentSubmission);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "submissionDate", target = "submissionDate")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "assignment", target = "assignmentInfo", qualifiedByName = "fromEntityToAssignmentDto")
    @Named("fromEntityToAssignmentSubmissionDto")
    AssignmentSubmissionDto fromEntityToAssignmentSubmissionDto(AssignmentSubmission assignmentSubmission);
    @IterableMapping(elementTargetType = AssignmentSubmissionDto.class, qualifiedByName = "fromEntityToAssignmentSubmissionDto")
    List<AssignmentSubmissionDto> fromEntityToAssignmentSubmissionDtoList(List<AssignmentSubmission> assignmentSubmissions);
}
