package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.assignment_submission.AssignmentSubmissionAdminDto;
import com.api.learning.ElearningBE.dto.assignment_submission.AssignmentSubmissionDto;
import com.api.learning.ElearningBE.exceptions.InvalidException;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.assignment_submission.CreateAssignmentSubmissionForm;
import com.api.learning.ElearningBE.form.assignment_submission.UpdateAssignmentSubmissionForm;
import com.api.learning.ElearningBE.services.assignment_submssion.AssignmentSubmissionService;
import com.api.learning.ElearningBE.storage.criteria.AssignmentSubmissionCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/assignment-submission")
public class AssignmentSubmissionController {

    private final AssignmentSubmissionService assignmentSubmissionService;

    public AssignmentSubmissionController(AssignmentSubmissionService assignmentSubmissionService) {
        this.assignmentSubmissionService = assignmentSubmissionService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ASSIGN_SUB_L')")
    public ApiMessageDto<ResponseListDto<List<AssignmentSubmissionAdminDto>>> list(AssignmentSubmissionCriteria assignmentSubmissionCriteria, Pageable pageable){
        ApiMessageDto<ResponseListDto<List<AssignmentSubmissionAdminDto>>> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = assignmentSubmissionService.list(assignmentSubmissionCriteria, pageable);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @GetMapping("/retrieve/{id}")
    @PreAuthorize("hasRole('ASSIGN_SUB_V')")
    public ApiMessageDto<AssignmentSubmissionAdminDto> retrieve(@PathVariable Long id){
        ApiMessageDto<AssignmentSubmissionAdminDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = assignmentSubmissionService.retrieve(id);
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

    @PostMapping("/submit")
    @PreAuthorize("hasRole('ASSIGN_SUB_C')")
    public ApiMessageDto<AssignmentSubmissionDto> submit(@Valid @RequestBody CreateAssignmentSubmissionForm createAssignmentSubmissionForm){
        ApiMessageDto<AssignmentSubmissionDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = assignmentSubmissionService.submit(createAssignmentSubmissionForm);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }catch (InvalidException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.BAD_REQUEST.toString());
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ASSIGN_SUB_U')")
    public ApiMessageDto<AssignmentSubmissionDto> update(@Valid @RequestBody UpdateAssignmentSubmissionForm updateAssignmentSubmissionForm){
        ApiMessageDto<AssignmentSubmissionDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = assignmentSubmissionService.update(updateAssignmentSubmissionForm);
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
    @PreAuthorize("hasRole('ASSIGN_SUB_D')")
    public ApiMessageDto<String> delete(@PathVariable Long id){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = assignmentSubmissionService.delete(id);
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
