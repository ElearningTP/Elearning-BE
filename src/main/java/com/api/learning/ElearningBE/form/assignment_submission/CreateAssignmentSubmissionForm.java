package com.api.learning.ElearningBE.form.assignment_submission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateAssignmentSubmissionForm {
    @ApiModelProperty(name = "textSubmission", notes = "The submission type is text")
    private String textSubmission;
    @ApiModelProperty(name = "fileSubmissionUrl", notes = "The submission type is file")
    private String fileSubmissionUrl;
    @ApiModelProperty(name = "linkSubmission", notes = "The submission type is link")
    private String linkSubmission;
    @NotNull(message = "Assignment id can not be null")
    @ApiModelProperty(name = "assignmentId", required = true)
    private Long assignmentId;
    @NotNull(message = "Course id can not be null")
    @ApiModelProperty(name = "courseId", required = true)
    private Long courseId;
}
