package com.api.learning.ElearningBE.form.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateAccountForm {
    @NotNull(message = "Id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;
    private String fullName;
    private String password;
    private String avatarPath;
    private Long nationId;
    private Long roleId;
}
