package com.api.learning.ElearningBE.form.role;

import com.api.learning.ElearningBE.validation.RoleKind;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateRoleForm {
    @NotEmpty(message = "Role name can not be empty")
    @ApiModelProperty(name = "roleName", required = true)
    private String roleName;
    @NotNull(message = "Role kind can not be null")
    @ApiModelProperty(name = "roleKind", required = true)
    @RoleKind
    private Integer roleKind;
    @ApiModelProperty(name = "description")
    private String description;
    @NotNull(message = "Permissions can not be null")
    @ApiModelProperty(name = "permissions", required = true)
    private List<Long> permissions;
}
