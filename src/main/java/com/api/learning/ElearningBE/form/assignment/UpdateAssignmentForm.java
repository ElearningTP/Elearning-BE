package com.api.learning.ElearningBE.form.assignment;

import com.api.learning.ElearningBE.validation.AssignmentState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UpdateAssignmentForm {
    @NotNull(message = "Id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;
    @NotEmpty(message = "Title can not be empty")
    @ApiModelProperty(name = "assignmentTitle", required = true)
    private String assignmentTitle;
    @ApiModelProperty(name = "assignmentContent")
    private String assignmentContent;
    @NotNull(message = "State can not be null")
    @ApiModelProperty(name = "state", required = true, notes = "This is state of assignment: 1 created, 2 started, 3 expired")
    @AssignmentState
    private Integer state;
    @ApiModelProperty(name = "startDate", example = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @ApiModelProperty(name = "endDate", example = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    @ApiModelProperty(name = "urlDocument")
    private String urlDocument;
}
