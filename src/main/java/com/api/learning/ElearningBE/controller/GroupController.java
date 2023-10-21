package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.form.group.CreateGroupForm;
import com.api.learning.ElearningBE.form.group.UpdateGroupForm;
import com.api.learning.ElearningBE.services.group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping("/create")
    public ApiMessageDto<String> create(@Valid @RequestBody CreateGroupForm createGroupForm){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = groupService.create(createGroupForm);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            apiMessageDto.setMessage("An unexpected error occurred: "+e.getMessage());
        }
        return apiMessageDto;
    }
    @PutMapping("/update")
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateGroupForm updateGroupForm){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = groupService.update(updateGroupForm);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            apiMessageDto.setMessage("An unexpected error occurred: "+e.getMessage());
        }
        return apiMessageDto;
    }
}
