package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.course_registration.CourseRegistrationDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.course_registration.CreateCourseRegistrationForm;
import com.api.learning.ElearningBE.services.course_registration.CourseRegistrationService;
import com.api.learning.ElearningBE.storage.criteria.CourseRegistrationCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/course-registration")
public class CourseRegistrationController {

    private final CourseRegistrationService courseRegistrationService;

    public CourseRegistrationController(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('REGISTRATION_L')")
    public ApiMessageDto<ResponseListDto<List<CourseRegistrationDto>>> list(CourseRegistrationCriteria courseRegistrationCriteria, Pageable pageable){
        ApiMessageDto<ResponseListDto<List<CourseRegistrationDto>>> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = courseRegistrationService.list(courseRegistrationCriteria,pageable);
        }catch (Exception e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @PostMapping("/enroll")
    @PreAuthorize("hasRole('REGISTRATION_C')")
    public ApiMessageDto<String> create(@Valid @RequestBody CreateCourseRegistrationForm createCourseRegistrationForm){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try{
            apiMessageDto = courseRegistrationService.enroll(createCourseRegistrationForm);
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
