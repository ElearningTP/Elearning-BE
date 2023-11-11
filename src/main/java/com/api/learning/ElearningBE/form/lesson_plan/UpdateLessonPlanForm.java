package com.api.learning.ElearningBE.form.lesson_plan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateLessonPlanForm {
    @NotNull(message = "Id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;
    @NotEmpty(message = "Name lesson plan can not be empty")
    @ApiModelProperty(name = "name",required = true)
    private String name;
    @ApiModelProperty(name = "description")
    private String description;
    @NotNull(message = "Status can not be null")
    @ApiModelProperty(name = "status", required = true)
    private Integer status;
}
