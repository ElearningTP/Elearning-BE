package com.api.learning.ElearningBE.form.notification;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateNotificationForm {
    @NotEmpty(message = "Message can not be empty")
    @ApiModelProperty(name = "message", required = true)
    private String message;
    @NotNull(message = "Account id can not be null")
    private Long accountId;
}
