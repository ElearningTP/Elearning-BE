package com.api.learning.ElearningBE.dto.role;

import lombok.Data;

@Data
public class RoleDto {
    private Long id;
    private String name;
    private Integer kind;
    private String description;
}
