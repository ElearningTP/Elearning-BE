package com.api.learning.ElearningBE.services.role;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.Role.RoleDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.role.CreateRoleForm;
import com.api.learning.ElearningBE.form.role.UpdateRoleForm;
import com.api.learning.ElearningBE.mapper.RoleMapper;
import com.api.learning.ElearningBE.repositories.RoleRepository;
import com.api.learning.ElearningBE.repositories.PermissionRepository;
import com.api.learning.ElearningBE.storage.entities.Role;
import com.api.learning.ElearningBE.storage.entities.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public ApiMessageDto<String> create(CreateRoleForm createRoleForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Boolean roleExisted = roleRepository.existsByName(createRoleForm.getRoleName().trim());
        if (roleExisted){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(String.format("Role name %s is existed", createRoleForm.getRoleName()));
            return apiMessageDto;
        }
        Role role = roleMapper.fromCreateGroupFormToEntity(createRoleForm);
        List<Permission> permissions = permissionRepository.findAllByIdIn(createRoleForm.getPermissions());
        role.setPermissions(permissions);
        roleRepository.save(role);

        apiMessageDto.setMessage("Create role successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> update(UpdateRoleForm updateRoleForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Role role = roleRepository.findById(updateRoleForm.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Role with id %s not found", updateRoleForm.getId())));

        roleMapper.fromUpdateGroupFormToEntity(updateRoleForm, role);
        List<Permission> permissions = permissionRepository.findAllByIdIn(updateRoleForm.getPermissions());
        role.setPermissions(permissions);
        roleRepository.save(role);

        apiMessageDto.setMessage("Update role successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<RoleDto> retrieve(Long id) {
        ApiMessageDto<RoleDto> apiMessageDto = new ApiMessageDto<>();
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Role with id %s not found", id)));
        RoleDto roleDto = roleMapper.fromEntityToRoleDto(role);

        apiMessageDto.setData(roleDto);
        apiMessageDto.setMessage("Retrieve role successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<List<RoleDto>> list() {
        ApiMessageDto<List<RoleDto>> apiMessageDto = new ApiMessageDto<>();
        List<Role> roles = roleRepository.findAll();
        List<RoleDto> roleDtos = roleMapper.fromEntityToRoleDtoList(roles);

        apiMessageDto.setData(roleDtos);
        apiMessageDto.setMessage("Retrieve role list successfully");
        return apiMessageDto;
    }
}
