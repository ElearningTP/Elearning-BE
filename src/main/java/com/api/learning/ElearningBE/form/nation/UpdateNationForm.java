package com.api.learning.ElearningBE.form.nation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateNationForm {
    @NotNull(message = "Id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;
    @NotEmpty(message = "Name can not be empty")
    @ApiModelProperty(name = "name", required = true)
    private String name;
    @NotNull(message = "Status can not be null")
    @ApiModelProperty(name = "status", required = true)
    private Integer status;
}
