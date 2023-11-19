package com.api.learning.ElearningBE.dto.course_registration;

import com.api.learning.ElearningBE.dto.account.AccountDto;
import com.api.learning.ElearningBE.dto.course.CourseDto;
import com.api.learning.ElearningBE.dto.modules.ModulesDto;
import lombok.Data;

import java.util.List;

@Data
public class CourseRegistrationDto {
    private Long id;
    private AccountDto studentInfo;
    private CourseDto courseInfo;
//    private List<ModulesDto> modulesInfo;
    private Integer status;
}
