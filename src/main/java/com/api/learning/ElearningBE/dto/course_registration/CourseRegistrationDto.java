package com.api.learning.ElearningBE.dto.course_registration;

import com.api.learning.ElearningBE.dto.account.AccountDto;
import com.api.learning.ElearningBE.dto.course.CourseDto;
import lombok.Data;

@Data
public class CourseRegistrationDto {
    private Long id;
    private AccountDto studentInfo;
    private CourseDto courseInfo;
    private Integer status;
}
