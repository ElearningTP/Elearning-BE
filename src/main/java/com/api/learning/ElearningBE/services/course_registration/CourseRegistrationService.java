package com.api.learning.ElearningBE.services.course_registration;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.course_registration.CourseRegistrationDto;
import com.api.learning.ElearningBE.form.course_registration.CreateCourseRegistrationForm;
import com.api.learning.ElearningBE.storage.criteria.CourseRegistrationCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseRegistrationService {
    ApiMessageDto<ResponseListDto<List<CourseRegistrationDto>>> list(CourseRegistrationCriteria courseRegistrationCriteria, Pageable pageable);
    ApiMessageDto<String> enroll(CreateCourseRegistrationForm createCourseRegistrationForm);
}
