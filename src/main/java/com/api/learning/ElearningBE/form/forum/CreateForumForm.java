package com.api.learning.ElearningBE.form.forum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateForumForm {
    @NotEmpty(message = "Title can not be empty")
    @ApiModelProperty(name = "forumTitle", required = true)
    private String forumTitle;
    @ApiModelProperty(name = "description")
    private String description;
    @NotNull(message = "Course id can not be null")
    @ApiModelProperty(name = "courseId", required = true)
    private Long courseId;
}
