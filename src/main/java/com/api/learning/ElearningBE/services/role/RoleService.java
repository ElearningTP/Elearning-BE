package com.api.learning.ElearningBE.services.role;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.Role.RoleDto;
import com.api.learning.ElearningBE.form.role.CreateRoleForm;
import com.api.learning.ElearningBE.form.role.UpdateRoleForm;

import java.util.List;

public interface RoleService {
    ApiMessageDto<String> create(CreateRoleForm createRoleForm);
    ApiMessageDto<String> update(UpdateRoleForm updateRoleForm);
    ApiMessageDto<RoleDto> get(Long id);
    ApiMessageDto<List<RoleDto>> list();
}
