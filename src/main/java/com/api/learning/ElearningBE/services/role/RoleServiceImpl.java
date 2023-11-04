package com.api.learning.ElearningBE.services.role;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.Role.RoleDto;
import com.api.learning.ElearningBE.form.role.CreateRoleForm;
import com.api.learning.ElearningBE.form.role.UpdateRoleForm;
import com.api.learning.ElearningBE.mapper.RoleMapper;
import com.api.learning.ElearningBE.repositories.RoleRepository;
import com.api.learning.ElearningBE.repositories.PermissionRepository;
import com.api.learning.ElearningBE.security.JwtUtils;
import com.api.learning.ElearningBE.storage.entities.Role;
import com.api.learning.ElearningBE.storage.entities.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public ApiMessageDto<String> create(CreateRoleForm createRoleForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Boolean groupExisted = roleRepository.existsByName(createRoleForm.getRoleName().trim());
        if (groupExisted){
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
        Role role = roleRepository.findById(updateRoleForm.getId()).orElse(null);
        if (role == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(String.format("Role with id %s not found", updateRoleForm.getId()));
            return apiMessageDto;
        }
        roleMapper.fromUpdateGroupFormToEntity(updateRoleForm, role);
        List<Permission> permissions = permissionRepository.findAllByIdIn(updateRoleForm.getPermissions());
        role.setPermissions(permissions);
        roleRepository.save(role);

        apiMessageDto.setMessage("Update role successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<RoleDto> get(Long id) {
        ApiMessageDto<RoleDto> apiMessageDto = new ApiMessageDto<>();
        Role role = roleRepository.findById(id).orElse(null);
        if (role == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(String.format("Role with id %s not found", id));
            return apiMessageDto;
        }
        RoleDto roleDto = roleMapper.fromEntityToRoleDto(role);

        apiMessageDto.setData(roleDto);
        apiMessageDto.setMessage("Get role successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<List<RoleDto>> list() {
        ApiMessageDto<List<RoleDto>> apiMessageDto = new ApiMessageDto<>();
        List<Role> roles = roleRepository.findAll();
        List<RoleDto> roleDtos = roleMapper.fromEntityToRoleDtoList(roles);

        apiMessageDto.setData(roleDtos);
        apiMessageDto.setMessage("Get list role successfully");
        return apiMessageDto;
    }
}
