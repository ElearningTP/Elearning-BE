package com.api.learning.ElearningBE.form.account;

//import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateAccountForm {
    @NotEmpty(message = "Email can not be empty")
    @ApiModelProperty(name = "email", required = true)
    private String email;
    @NotEmpty(message = "Password can not be empty")
    @ApiModelProperty(name = "password", required = true)
    private String password;
    @NotEmpty(message = "Full name can not be empty")
    @ApiModelProperty(name = "fullName", required = true)
    private String fullName;
}
