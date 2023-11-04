package com.api.learning.ElearningBE.dto.permission;

import lombok.Data;

@Data
public class PermissionDto {
    private String name;
    private String action;
    private Boolean showMenu;
    private String description;
    private String roleName;
    private String permissionCode;
}
