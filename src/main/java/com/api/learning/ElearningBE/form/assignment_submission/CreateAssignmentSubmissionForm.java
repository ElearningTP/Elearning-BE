package com.api.learning.ElearningBE.form.assignment_submission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateAssignmentSubmissionForm {
    @NotNull(message = "Submission date can not be null")
    @ApiModelProperty(name = "submissionDate", required = true, example = "yyyy-MM-dd HH:mm:ss")
    private Date submissionDate;
    @ApiModelProperty(name = "textSubmission", notes = "The submission type is text")
    private String textSubmission;
    @ApiModelProperty(name = "fileSubmissionUrl", notes = "The submission type is file")
    private String fileSubmissionUrl;
    @NotNull(message = "Assignment id can not be null")
    @ApiModelProperty(name = "assignmentId", required = true)
    private Long assignmentId;
    @NotNull(message = "Modules id can not be null")
    @ApiModelProperty(name = "studentId", required = true)
    private Long studentId;
    @NotNull(message = "Course id can not be null")
    @ApiModelProperty(name = "courseId", required = true)
    private Long courseId;
}
