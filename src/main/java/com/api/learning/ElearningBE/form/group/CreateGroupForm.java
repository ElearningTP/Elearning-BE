package com.api.learning.ElearningBE.form.group;

import com.api.learning.ElearningBE.validation.GroupKind;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateGroupForm {
    @NotEmpty(message = "Group name can not be empty")
    @ApiModelProperty(name = "groupName", required = true)
    private String groupName;
    @NotNull(message = "Group kind can not be null")
    @ApiModelProperty(name = "groupKind", required = true)
    @GroupKind
    private Integer groupKind;
    @ApiModelProperty(name = "description")
    private String description;
    @NotNull(message = "Permissions can not be null")
    @ApiModelProperty(name = "permissions", required = true)
    private List<Long> permissions;
}
