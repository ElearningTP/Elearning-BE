package com.api.learning.ElearningBE.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {
    @NotEmpty(message = "email can not be empty")
    @ApiModelProperty(name = "email", required = true)
    private String email;
    @NotEmpty(message = "password can not be empty")
    @ApiModelProperty(name = "password", required = true)
    private String password;
}
