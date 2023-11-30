package com.api.learning.ElearningBE.form.topic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateTopicForm {
    @NotNull(message = "Id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;
    @NotEmpty(message = "Topic title can not be empty")
    @ApiModelProperty(name = "topicTitle", required = true)
    private String topicTitle;
    @ApiModelProperty(name = "topicContent")
    private String topicContent;
}