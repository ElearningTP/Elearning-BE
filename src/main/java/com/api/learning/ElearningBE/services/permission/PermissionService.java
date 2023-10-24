package com.api.learning.ElearningBE.services.permission;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.form.permission.CreatePermissionForm;

public interface PermissionService {
    ApiMessageDto<String> create(CreatePermissionForm createPermissionForm);
}
