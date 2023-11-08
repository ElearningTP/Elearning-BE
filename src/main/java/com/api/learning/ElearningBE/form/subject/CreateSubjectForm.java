package com.api.learning.ElearningBE.form.subject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateSubjectForm {
    @NotEmpty(message = "Subject name can not be empty")
    @ApiModelProperty(name = "subjectName", required = true)
    private String subjectName;
    @NotEmpty(message = "Subject code can not be empty")
    @ApiModelProperty(name = "subjectCode", required = true)
    private String subjectCode;
    @ApiModelProperty(name = "description")
    private String description;
}
