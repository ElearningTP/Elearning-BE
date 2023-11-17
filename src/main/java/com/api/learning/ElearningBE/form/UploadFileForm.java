package com.api.learning.ElearningBE.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UploadFileForm {
    @NotEmpty(message = "Type is required")
    @ApiModelProperty(name = "type", required = true)
    private String type;
    @NotNull(message = "File is required")
    @ApiModelProperty(name = "file", required = true)
    private MultipartFile file;
}
