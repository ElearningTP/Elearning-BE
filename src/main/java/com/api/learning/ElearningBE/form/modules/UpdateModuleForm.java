package com.api.learning.ElearningBE.form.modules;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateModuleForm {
    @NotNull(message = "Id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;
    @NotEmpty(message = "Modules name can not be empty")
    @ApiModelProperty(name = "modulesName", required = true)
    private String modulesName;
    @ApiModelProperty(name = "description")
    private String description;
}
