package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.assignment_submission.AssignmentSubmissionAdminDto;
import com.api.learning.ElearningBE.form.assignment_submission.CreateAssignmentSubmissionForm;
import com.api.learning.ElearningBE.form.assignment_submission.UpdateAssignmentSubmissionForm;
import com.api.learning.ElearningBE.storage.entities.AssignmentSubmission;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
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
}
