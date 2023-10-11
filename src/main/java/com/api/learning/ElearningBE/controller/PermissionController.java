package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.form.permission.CreatePermissionForm;
import com.api.learning.ElearningBE.services.permission.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @PostMapping("/create")
    public ApiMessageDto<String> create(@Valid @RequestBody CreatePermissionForm createPermissionForm){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = permissionService.create(createPermissionForm);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            apiMessageDto.setMessage(e.getMessage());
        }
        return apiMessageDto;
    }
}
