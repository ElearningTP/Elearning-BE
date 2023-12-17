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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ASSIGN_L')")
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
    @PreAuthorize("hasRole('ASSIGN_V')")
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
    @PreAuthorize("hasRole('ASSIGN_C')")
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
    @PreAuthorize("hasRole('ASSIGN_U')")
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
    @PreAuthorize("hasRole('ASSIGN_D')")
    public ResponseEntity<ApiMessageDto<String>> delete(@PathVariable Long id){
        ResponseEntity<ApiMessageDto<String>> response;
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = assignmentService.delete(id);
            response = new ResponseEntity<>(apiMessageDto, HttpStatus.OK);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
            response = new ResponseEntity<>(apiMessageDto, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            response = new ResponseEntity<>(apiMessageDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
