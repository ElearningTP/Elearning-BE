package com.api.learning.ElearningBE.form.permission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreatePermissionForm {
    @NotEmpty(message = "Permission name can not be empty")
    @ApiModelProperty(name = "permissionName", required = true)
    private String permissionName;
    @NotEmpty(message = "Permission action can not be empty")
    @ApiModelProperty(name = "permissionAction", required = true)
    private String permissionAction;
    @ApiModelProperty(name = "showMenu")
    private Boolean showMenu;
    @ApiModelProperty(name = "description")
    private String description;
    @NotEmpty(message = "Name group can not be empty")
    @ApiModelProperty(name = "nameGroup", required = true)
    private String nameGroup;
    @NotEmpty(message = "Permission code can not be empty")
    @ApiModelProperty(name = "permissionCode", required = true)
    private String permissionCode;
}
