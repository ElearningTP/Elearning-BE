package com.api.learning.ElearningBE.dto;


import lombok.Data;

import java.util.Date;

@Data
public class BaseAdminDto {
    private Long id;
    private Date createDate;
    private Date modifiedDate;
    private Integer status;
}
