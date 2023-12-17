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
    @ApiModelProperty(name = "textSubmission", notes = "The submission type is text")
    private String textSubmission;
    @ApiModelProperty(name = "fileSubmissionUrl", notes = "The submission type is file")
    private String fileSubmissionUrl;
    @ApiModelProperty(name = "linkSubmission", notes = "The submission type is link")
    private String linkSubmission;
    @ApiModelProperty(name = "score")
    private Double score;
}
