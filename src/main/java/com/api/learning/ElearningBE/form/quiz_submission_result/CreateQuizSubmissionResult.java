package com.api.learning.ElearningBE.form.quiz_submission_result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateQuizSubmissionResult {
    @NotNull(message = "Answer id can not be null")
    @ApiModelProperty(name = "answerId", required = true)
    private Long answerId;
}
