package com.api.learning.ElearningBE.form.lecture;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateLectureForm {
    @NotNull(message = "Id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;
    @NotEmpty(message = "Lecture name can not be empty")
    @ApiModelProperty(name = "lectureName", required = true)
    private String lectureName;
    @NotEmpty(message = "Lecture content can not be empty")
    @ApiModelProperty(name = "lectureContent", required = true)
    private String lectureContent;
}
