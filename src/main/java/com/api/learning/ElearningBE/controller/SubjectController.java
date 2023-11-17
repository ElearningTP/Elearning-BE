package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.subject.SubjectAdminDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.subject.CreateSubjectForm;
import com.api.learning.ElearningBE.form.subject.UpdateSubjectForm;
import com.api.learning.ElearningBE.services.subject.SubjectService;
import com.api.learning.ElearningBE.storage.criteria.SubjectCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('SUB_L')")
    public ApiMessageDto<ResponseListDto<List<SubjectAdminDto>>> list(SubjectCriteria subjectCriteria, Pageable pageable){
        ApiMessageDto<ResponseListDto<List<SubjectAdminDto>>> apiMessageDto = new ApiMessageDto<>();
        try{
            apiMessageDto = subjectService.list(subjectCriteria,pageable);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @GetMapping("/retrieve/{id}")
    @PreAuthorize("hasRole('SUB_V')")
    public ApiMessageDto<SubjectAdminDto> retrieve(@PathVariable Long id){
        ApiMessageDto<SubjectAdminDto> apiMessageDto = new ApiMessageDto<>();
        try{
            apiMessageDto = subjectService.retrieve(id);
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
    @PreAuthorize("hasRole('SUB_C')")
    public ApiMessageDto<String> create(@Valid @RequestBody CreateSubjectForm createSubjectForm){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try{
            apiMessageDto = subjectService.create(createSubjectForm);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('SUB_U')")
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateSubjectForm updateSubjectForm){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try{
            apiMessageDto = subjectService.update(updateSubjectForm);
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
    @PreAuthorize("hasRole('SUB_D')")
    public ApiMessageDto<String> delete(@PathVariable Long id){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try{
            apiMessageDto = subjectService.delete(id);
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
