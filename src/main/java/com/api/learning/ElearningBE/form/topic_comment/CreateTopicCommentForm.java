package com.api.learning.ElearningBE.form.topic_comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateTopicCommentForm {
    @NotEmpty(message = "Content can not be empty")
    @ApiModelProperty(name = "content", required = true)
    private String content;
    @NotNull(message = "Topic id can not be null")
    @ApiModelProperty(name = "topicId", required = true)
    private Long topicId;
    @NotNull(message = "Account id can not be null")
    @ApiModelProperty(name = "accountId", required = true)
    private Long accountId;
}
