package com.api.learning.ElearningBE.form.assignment;

import com.api.learning.ElearningBE.validation.AssignmentState;
import com.api.learning.ElearningBE.validation.AssignmentType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateAssignmentForm {
    @NotEmpty(message = "Title can not be empty")
    @ApiModelProperty(name = "assignmentTitle", required = true)
    private String assignmentTitle;
    @ApiModelProperty(name = "assignmentContent")
    private String assignmentContent;
    @NotNull(message = "Assignment type can not be null")
    @ApiModelProperty(name = "assignmentType", required = true, notes = "This is assignment type: 1 file, 2 text")
    @AssignmentType
    private Integer assignmentType;
    @NotNull(message = "State can not be null")
    @ApiModelProperty(name = "state", required = true, notes = "This is state of assignment: 1 created, 2 started, 3 expired")
    @AssignmentState
    private Integer state;
    @ApiModelProperty(name = "startDate", example = "dd/MM/yyyy HH:mm:ss")
    private Date startDate;
    @ApiModelProperty(name = "endDate", example = "dd/MM/yyyy HH:mm:ss")
    private Date endDate;
    @ApiModelProperty(name = "urlDocument")
    private String urlDocument;
    @NotNull(message = "modulesId can not be null")
    @ApiModelProperty(name = "modulesId", required = true)
    private Long modulesId;
}
