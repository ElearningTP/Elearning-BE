package com.api.learning.ElearningBE.services.course;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.course.CourseAdminDto;
import com.api.learning.ElearningBE.dto.course.CourseDto;
import com.api.learning.ElearningBE.form.course.CreateCourseForm;
import com.api.learning.ElearningBE.storage.criteria.CourseCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {
    ApiMessageDto<ResponseListDto<List<CourseDto>>> autoComplete(CourseCriteria courseCriteria, Pageable pageable);
    ApiMessageDto<CourseAdminDto> retrieve(Long id);
    ApiMessageDto<ResponseListDto<List<CourseDto>>> list(CourseCriteria courseCriteria, Pageable pageable);
    ApiMessageDto<String> create(CreateCourseForm createCourseForm);
}
