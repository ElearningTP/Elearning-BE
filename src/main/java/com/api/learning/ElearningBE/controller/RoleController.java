package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.Role.RoleDto;
import com.api.learning.ElearningBE.form.role.CreateRoleForm;
import com.api.learning.ElearningBE.form.role.UpdateRoleForm;
import com.api.learning.ElearningBE.security.JwtUtils;
import com.api.learning.ElearningBE.services.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ApiMessageDto<List<RoleDto>> list(){
        ApiMessageDto<List<RoleDto>> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = roleService.list();
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            apiMessageDto.setMessage(e.getMessage());
        }
        return apiMessageDto;
    }

    @GetMapping("/get/{id}")
    public ApiMessageDto<RoleDto> get(@PathVariable Long id){
        ApiMessageDto<RoleDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = roleService.get(id);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            apiMessageDto.setMessage(e.getMessage());
        }
        return apiMessageDto;
    }
    @PostMapping("/create")
    public ApiMessageDto<String> create(@Valid @RequestBody CreateRoleForm createRoleForm){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = roleService.create(createRoleForm);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            apiMessageDto.setMessage(e.getMessage());
        }
        return apiMessageDto;
    }
    @PutMapping("/update")
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateRoleForm updateRoleForm){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = roleService.update(updateRoleForm);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            apiMessageDto.setMessage(e.getMessage());
        }
        return apiMessageDto;
    }
}
