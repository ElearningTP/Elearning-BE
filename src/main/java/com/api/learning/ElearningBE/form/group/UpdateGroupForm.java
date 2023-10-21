package com.api.learning.ElearningBE.form.group;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UpdateGroupForm {
    @NotNull(message = "Id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;
    @NotEmpty(message = "Group name can not be empty")
    @ApiModelProperty(name = "groupName", required = true)
    private String groupName;
    @ApiModelProperty(name = "description")
    private String description;
    @NotNull(message = "Permissions can not be null")
    @ApiModelProperty(name = "permissions", required = true)
    private List<Long> permissions;
}
