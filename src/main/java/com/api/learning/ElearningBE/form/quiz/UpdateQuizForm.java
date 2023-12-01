package com.api.learning.ElearningBE.form.quiz;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UpdateQuizForm {
    @NotNull(message = "Id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;
    @NotEmpty(message = "Quiz title can not be empty")
    @ApiModelProperty(name = "quizTitle", required = true)
    private String quizTitle;
    @ApiModelProperty(name = "description")
    private String description;
    @NotNull(message = "Quiz time limit can not be null")
    @ApiModelProperty(name = "quizTimeLimit", required = true, notes = "This attribute stores the quiz time limit")
    private Long quizTimeLimit;
    @NotNull(message = "Start date can not be null")
    @ApiModelProperty(name = "startDate", required = true, example = "yyyy-MM-dd HH:mm:ss", notes = "This attribute stores the quiz time opened")
    private Date startDate;
    @NotNull(message = "End date can not be null")
    @ApiModelProperty(name = "endDate", required = true, example = "yyyy-MM-dd HH:mm:ss", notes = "This attribute stores the quiz time closed")
    private Date endDate;
}
