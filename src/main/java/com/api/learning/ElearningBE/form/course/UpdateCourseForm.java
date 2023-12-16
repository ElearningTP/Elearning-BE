package com.api.learning.ElearningBE.form.course;

import com.api.learning.ElearningBE.validation.CourseState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class UpdateCourseForm {
    @NotNull(message = "Course id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;
    @ApiModelProperty(name = "courseName")
    private String courseName;
    @ApiModelProperty(name = "courseName")
    private String thumbnail;
    @ApiModelProperty(name = "state")
    @CourseState
    private Integer state;
    @ApiModelProperty(name = "startDate", example = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @ApiModelProperty(name = "requirements")
    private List<String> requirements;
    @ApiModelProperty(name = "objectives")
    private List<String> objectives;
    @ApiModelProperty(name = "description")
    private String description;
    @ApiModelProperty(name = "teacherId")
    private Long teacherId;
    @ApiModelProperty(name = "lessonPlanId")
    private Long lessonPlanId;
    @ApiModelProperty(name = "categoryId")
    private Long categoryId;
}
