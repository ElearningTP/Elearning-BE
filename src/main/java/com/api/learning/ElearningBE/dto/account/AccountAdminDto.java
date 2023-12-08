package com.api.learning.ElearningBE.dto.account;

import com.api.learning.ElearningBE.dto.BaseAdminDto;
import com.api.learning.ElearningBE.dto.role.RoleAdminDto;
import com.api.learning.ElearningBE.dto.nation.NationAdminDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class AccountAdminDto extends BaseAdminDto {
    private String fullName;
    private String email;
    private Integer kind;
    private String avatarPath;
    private Boolean isSuperAdmin = false;
    private Date lastLogin;
    private NationAdminDto nationInfo;
    private RoleAdminDto roleInfo;
}
