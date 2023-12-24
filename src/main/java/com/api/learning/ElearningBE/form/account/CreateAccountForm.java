package com.api.learning.ElearningBE.form.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateAccountForm {
    @NotEmpty(message = "Full name can not be empty")
    @ApiModelProperty(name = "fullName", required = true)
    private String fullName;
    @NotEmpty(message = "Email can not be empty")
    @ApiModelProperty(name = "email", required = true)
//    @Email(message = "Email is not incorrect format")
    private String email;
    @NotEmpty(message = "Password can not be empty")
    @ApiModelProperty(name = "password", required = true)
    private String password;
    @NotNull(message = "Kind can not be null")
    @ApiModelProperty(name = "kind", required = true)
    private Integer kind;
    @ApiModelProperty(name = "avatarPath")
    private String avatarPath;
    @ApiModelProperty(name = "nationId")
    private Long nationId;
    @NotNull(message = "Role id can not be null")
    @ApiModelProperty(name = "roleId", required = true)
    private Long roleId;
}
