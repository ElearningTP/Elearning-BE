package com.api.learning.ElearningBE.form.topic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateTopicForm {
    @ApiModelProperty(name = "topicContent")
    private String topicContent;
    @NotNull(message = "Forum id can not be null")
    @ApiModelProperty(name = "forumId", required = true)
    private Long forumId;
    @NotNull(message = "Account id can not be null")
    @ApiModelProperty(name = "accountId", required = true)
    private Long accountId;
}
