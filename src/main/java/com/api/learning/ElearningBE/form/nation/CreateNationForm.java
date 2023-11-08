package com.api.learning.ElearningBE.form.nation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateNationForm {
    @NotEmpty(message = "Name can not be empty")
    @ApiModelProperty(name = "name")
    private String name;
}
