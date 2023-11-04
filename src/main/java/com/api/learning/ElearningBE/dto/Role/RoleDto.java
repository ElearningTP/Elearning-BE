package com.api.learning.ElearningBE.dto.Role;

import com.api.learning.ElearningBE.dto.BaseAdminDto;
import com.api.learning.ElearningBE.dto.permission.PermissionDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleDto extends BaseAdminDto {
    private String name;
    private Integer kind;
    private String description;
    private List<PermissionDto> permissions;
}
