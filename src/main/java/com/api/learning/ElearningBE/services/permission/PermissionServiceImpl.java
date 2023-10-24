package com.api.learning.ElearningBE.services.permission;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.form.permission.CreatePermissionForm;
import com.api.learning.ElearningBE.mapper.PermissionMapper;
import com.api.learning.ElearningBE.repositories.PermissionRepository;
import com.api.learning.ElearningBE.storage.entities.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService{
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public ApiMessageDto<String> create(CreatePermissionForm createPermissionForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Boolean pCodeExisted = permissionRepository.existsByPermissionCode(createPermissionForm.getPermissionCode().trim());
        if (pCodeExisted){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(String.format("Permission code %s is existed", createPermissionForm.getPermissionCode()));
            return apiMessageDto;
        }
        Permission permission = permissionMapper.fromCreatePermissionFormToEntity(createPermissionForm);
        permissionRepository.save(permission);

        apiMessageDto.setMessage("Create permission successfully");
        return apiMessageDto;
    }
}
