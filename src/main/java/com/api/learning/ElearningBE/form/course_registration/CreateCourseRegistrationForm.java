package com.api.learning.ElearningBE.form.course_registration;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateCourseRegistrationForm {
    @NotNull(message = "Student id can not be null")
    @ApiModelProperty(name = "studentId", required = true)
    private Long studentId;
    @NotNull(message = "Course id can not be null")
    @ApiModelProperty(name = "courseId", required = true)
    private Long courseId;
}
