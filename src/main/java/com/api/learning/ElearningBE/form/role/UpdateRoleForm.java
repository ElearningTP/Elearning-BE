package com.api.learning.ElearningBE.form.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UpdateRoleForm {
    @NotNull(message = "Id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;
    @NotEmpty(message = "Role name can not be empty")
    @ApiModelProperty(name = "roleName", required = true)
    private String roleName;
    @ApiModelProperty(name = "description")
    private String description;
    @NotNull(message = "Permissions can not be null")
    @ApiModelProperty(name = "permissions", required = true)
    private List<Long> permissions;
}
