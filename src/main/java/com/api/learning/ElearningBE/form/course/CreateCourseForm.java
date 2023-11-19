package com.api.learning.ElearningBE.form.course;

import com.api.learning.ElearningBE.validation.CourseState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class CreateCourseForm {
    @NotEmpty(message = "Course name can not be empty")
    @ApiModelProperty(name = "courseName", required = true)
    private String courseName;
    @ApiModelProperty(name = "courseName")
    private String thumbnail;
    @NotNull(message = "State can not be null")
    @ApiModelProperty(name = "state", required = true)
    @CourseState
    private Integer state;
    @NotNull(message = "Start date can not be null")
    @ApiModelProperty(name = "startDate", required = true)
    private Date startDate;
    @ApiModelProperty(name = "requirements")
    private List<String> requirements;
    @ApiModelProperty(name = "objectives")
    private List<String> objectives;
    @ApiModelProperty(name = "description")
    private String description;
    @NotNull(message = "Teacher id can not be null")
    @ApiModelProperty(name = "teacherId", required = true)
    private Long teacherId;
    @ApiModelProperty(name = "lessonPlanId")
    private Long lessonPlanId;
    @NotNull(message = "Category id can not be null")
    @ApiModelProperty(name = "categoryId", required = true)
    private Long categoryId;
}
