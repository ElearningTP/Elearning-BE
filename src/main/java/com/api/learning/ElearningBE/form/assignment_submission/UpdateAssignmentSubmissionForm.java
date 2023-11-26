package com.api.learning.ElearningBE.form.assignment_submission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UpdateAssignmentSubmissionForm {
    @NotNull(message = "Id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;
    @NotNull(message = "Submission date can not be null")
    @ApiModelProperty(name = "submissionDate", required = true, example = "dd/MM/yyyy HH:mm:ss")
    private Date submissionDate;
    @ApiModelProperty(name = "textSubmission", notes = "The submission type is text")
    private String textSubmission;
    @ApiModelProperty(name = "fileSubmissionUrl", notes = "The submission type is file")
    private String fileSubmissionUrl;
}
