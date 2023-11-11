package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.Role.RoleAdminDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.role.CreateRoleForm;
import com.api.learning.ElearningBE.form.role.UpdateRoleForm;
import com.api.learning.ElearningBE.services.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('ROL_L')")
    public ApiMessageDto<List<RoleAdminDto>> list(){
        ApiMessageDto<List<RoleAdminDto>> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = roleService.list();
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            apiMessageDto.setMessage(e.getMessage());
        }
        return apiMessageDto;
    }

    @GetMapping("/retrieve/{id}")
    @PreAuthorize("hasRole('ROL_V')")
    public ApiMessageDto<RoleAdminDto> retrieve(@PathVariable Long id){
        ApiMessageDto<RoleAdminDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = roleService.retrieve(id);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            apiMessageDto.setMessage(e.getMessage());
        }
        return apiMessageDto;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROL_C')")
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
    @PreAuthorize("hasRole('ROL_U')")
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateRoleForm updateRoleForm){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = roleService.update(updateRoleForm);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            apiMessageDto.setMessage(e.getMessage());
        }
        return apiMessageDto;
    }
}
