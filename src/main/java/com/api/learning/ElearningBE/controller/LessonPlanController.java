package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.lesson_plan.LessonPlanAdminDto;
import com.api.learning.ElearningBE.dto.lesson_plan.LessonPlanDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.exceptions.UnauthorizedException;
import com.api.learning.ElearningBE.form.lesson_plan.CreateLessonPlanForm;
import com.api.learning.ElearningBE.form.lesson_plan.UpdateLessonPlanForm;
import com.api.learning.ElearningBE.services.lesson_plan.LessonPlanService;
import com.api.learning.ElearningBE.storage.criteria.LessonPlanCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/lesson-plan")
public class LessonPlanController {

    @Autowired
    private LessonPlanService lessonPlanService;

    @GetMapping("/auto-complete")
    public ApiMessageDto<ResponseListDto<List<LessonPlanDto>>> autoComplete(LessonPlanCriteria lessonPlanCriteria, Pageable pageable){
        ApiMessageDto<ResponseListDto<List<LessonPlanDto>>> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = lessonPlanService.autoComplete(lessonPlanCriteria, pageable);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('LESS_P_L')")
    public ApiMessageDto<ResponseListDto<List<LessonPlanDto>>> list(LessonPlanCriteria lessonPlanCriteria, Pageable pageable){
        ApiMessageDto<ResponseListDto<List<LessonPlanDto>>> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = lessonPlanService.list(lessonPlanCriteria, pageable);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @GetMapping("/retrieve/{id}")
    @PreAuthorize("hasRole('LESS_P_V')")
    public ApiMessageDto<LessonPlanAdminDto> retrieve(@PathVariable Long id){
        ApiMessageDto<LessonPlanAdminDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = lessonPlanService.retrieve(id);
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
    @PreAuthorize("hasRole('LESS_P_C')")
    public ApiMessageDto<String> create(@Valid @RequestBody CreateLessonPlanForm createLessonPlanForm){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try{
            apiMessageDto = lessonPlanService.create(createLessonPlanForm);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }catch (UnauthorizedException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.UNAUTHORIZED.toString());
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('LESS_P_U')")
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateLessonPlanForm updateLessonPlanForm){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = lessonPlanService.update(updateLessonPlanForm);
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
    @PreAuthorize("hasRole('LESS_P_D')")
    public ApiMessageDto<String> delete(@PathVariable Long id
                                        ){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = lessonPlanService.delete(id);
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
