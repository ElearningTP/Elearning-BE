package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.assignment.AssignmentAdminDto;
import com.api.learning.ElearningBE.dto.assignment.AssignmentDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.assignment.CreateAssignmentForm;
import com.api.learning.ElearningBE.form.assignment.UpdateAssignmentForm;
import com.api.learning.ElearningBE.services.assignment.AssignmentService;
import com.api.learning.ElearningBE.storage.criteria.AssignmentCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping("/list")
    public ApiMessageDto<ResponseListDto<List<AssignmentDto>>> list(AssignmentCriteria assignmentCriteria, Pageable pageable){
        ApiMessageDto<ResponseListDto<List<AssignmentDto>>> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = assignmentService.list(assignmentCriteria, pageable);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @GetMapping("/retrieve/{id}")
    public ApiMessageDto<AssignmentAdminDto> retrieve(@PathVariable Long id){
        ApiMessageDto<AssignmentAdminDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = assignmentService.retrieve(id);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @PostMapping("/create")
    public ApiMessageDto<String> create(@Valid @RequestBody CreateAssignmentForm createAssignmentForm){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = assignmentService.create(createAssignmentForm);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @PutMapping("/update")
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateAssignmentForm updateAssignmentForm){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = assignmentService.update(updateAssignmentForm);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @DeleteMapping("/delete/{id}")
    public ApiMessageDto<String> delete(@PathVariable Long id){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = assignmentService.delete(id);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }
}
