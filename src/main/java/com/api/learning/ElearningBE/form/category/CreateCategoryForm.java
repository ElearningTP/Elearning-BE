package com.api.learning.ElearningBE.form.category;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateCategoryForm {
    @NotEmpty(message = "Category name can not be empty")
    @ApiModelProperty(name = "name", required = true)
    private String name;
}
