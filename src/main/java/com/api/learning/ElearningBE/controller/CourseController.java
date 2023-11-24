package com.api.learning.ElearningBE.controller;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.course.CourseAdminDto;
import com.api.learning.ElearningBE.dto.course.CourseDto;
import com.api.learning.ElearningBE.exceptions.InvalidException;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.form.course.CreateCourseForm;
import com.api.learning.ElearningBE.services.course.CourseService;
import com.api.learning.ElearningBE.storage.criteria.CourseCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/auto-complete")
    public ApiMessageDto<ResponseListDto<List<CourseDto>>> autoComplete(CourseCriteria courseCriteria, Pageable pageable){
        ApiMessageDto<ResponseListDto<List<CourseDto>>> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = courseService.autoComplete(courseCriteria, pageable);
        }catch (Exception e) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('COURSE_L')")
    public ApiMessageDto<ResponseListDto<List<CourseDto>>> list(CourseCriteria courseCriteria, Pageable pageable){
        ApiMessageDto<ResponseListDto<List<CourseDto>>> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = courseService.list(courseCriteria, pageable);
        }catch (Exception e) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @GetMapping("/retrieve/{id}")
    @PreAuthorize("hasRole('COURSE_V')")
    public ApiMessageDto<CourseAdminDto> retrieve(@PathVariable Long id){
        ApiMessageDto<CourseAdminDto> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = courseService.retrieve(id);
        }catch (NotFoundException e){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.NOT_FOUND.toString());
        }catch (Exception e) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
            apiMessageDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return apiMessageDto;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('COURSE_C')")
    public ApiMessageDto<String> create(@Valid @RequestBody CreateCourseForm createCourseForm){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            apiMessageDto = courseService.create(createCourseForm);
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
}
