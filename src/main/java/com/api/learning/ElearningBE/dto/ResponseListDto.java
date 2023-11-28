package com.api.learning.ElearningBE.dto;

import lombok.Data;

@Data
public class ResponseListDto<T>{
    private static long serialVersionUID = 1L;
    private T content;
    private Integer totalPages;
    private Long totalElements;
    private Integer pageIndex;
    private Integer pageSize;
}
