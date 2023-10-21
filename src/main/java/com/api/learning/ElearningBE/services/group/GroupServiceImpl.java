package com.api.learning.ElearningBE.services.group;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.form.group.CreateGroupForm;
import com.api.learning.ElearningBE.form.group.UpdateGroupForm;
import com.api.learning.ElearningBE.mapper.GroupMapper;
import com.api.learning.ElearningBE.repositories.GroupRepository;
import com.api.learning.ElearningBE.repositories.PermissionRepository;
import com.api.learning.ElearningBE.storage.entities.Group;
import com.api.learning.ElearningBE.storage.entities.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService{
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public ApiMessageDto<String> create(CreateGroupForm createGroupForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Boolean groupExisted = groupRepository.existsByName(createGroupForm.getGroupName().trim());
        if (groupExisted){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(String.format("Group name %s is existed", createGroupForm.getGroupName()));
            return apiMessageDto;
        }
        Group group = groupMapper.fromCreateGroupFormToEntity(createGroupForm);
        List<Permission> permissions = permissionRepository.findAllByIdIn(createGroupForm.getPermissions());
        group.setPermissions(permissions);
        groupRepository.save(group);

        apiMessageDto.setMessage("Create group successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<String> update(UpdateGroupForm updateGroupForm) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Group group = groupRepository.findById(updateGroupForm.getId()).orElse(null);
        if (group == null){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(String.format("Group with id %s not found", updateGroupForm.getId()));
            return apiMessageDto;
        }
        groupMapper.fromUpdateGroupFormToEntity(updateGroupForm, group);
        List<Permission> permissions = permissionRepository.findAllByIdIn(updateGroupForm.getPermissions());
        group.setPermissions(permissions);
        groupRepository.save(group);

        apiMessageDto.setMessage("Update group successfully");
        return apiMessageDto;
    }
}
