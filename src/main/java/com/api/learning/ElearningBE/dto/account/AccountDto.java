package com.api.learning.ElearningBE.dto.account;

import com.api.learning.ElearningBE.dto.role.RoleDto;
import com.api.learning.ElearningBE.dto.nation.NationDto;
import lombok.Data;

@Data
public class AccountDto {
    private Long id;
    private String fullName;
    private String email;
    private Integer kind;
    private String avatarPath;
    private Boolean isSuperAdmin = false;
    private NationDto nationInfo;
    private RoleDto roleInfo;
}
