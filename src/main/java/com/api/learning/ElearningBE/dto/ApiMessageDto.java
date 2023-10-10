package com.api.learning.ElearningBE.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class ApiMessageDto<T> {
    private Boolean result = true;
    private T data = null;
    private String code = null;
    private String message = null;
}
