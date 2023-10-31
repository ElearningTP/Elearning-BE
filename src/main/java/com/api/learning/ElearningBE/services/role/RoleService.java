package com.api.learning.ElearningBE.services.role;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.form.role.CreateRoleForm;
import com.api.learning.ElearningBE.form.role.UpdateRoleForm;

public interface RoleService {
    ApiMessageDto<String> create(CreateRoleForm createRoleForm);
    ApiMessageDto<String> update(UpdateRoleForm updateRoleForm);
}
