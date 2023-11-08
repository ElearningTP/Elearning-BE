package com.api.learning.ElearningBE.form.subject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateSubjectForm {
    @NotNull(message = "Id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;
    @NotEmpty(message = "Subject name can not be empty")
    @ApiModelProperty(name = "subjectName", required = true)
    private String subjectName;
    @NotEmpty(message = "Subject code can not be empty")
    @ApiModelProperty(name = "subjectCode", required = true)
    private String subjectCode;
    @ApiModelProperty(name = "description")
    private String description;
    @NotNull(message = "Status can not be null")
    @ApiModelProperty(name = "status", required = true)
    private String status;
}
