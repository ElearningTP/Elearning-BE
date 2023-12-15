package com.api.learning.ElearningBE.form.resources;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateResourcesForm {
    @NotNull(message = "Id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;
    @NotEmpty(message = "Title can not be empty")
    @ApiModelProperty(name = "title", required = true)
    private String title;
    @NotEmpty(message = "Url document can not be null")
    @ApiModelProperty(name = "urlDocument", required = true)
    private String urlDocument;
}
