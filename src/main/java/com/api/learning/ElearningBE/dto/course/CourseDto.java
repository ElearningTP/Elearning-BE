package com.api.learning.ElearningBE.dto.course;

import com.api.learning.ElearningBE.dto.account.AccountDto;
import com.api.learning.ElearningBE.dto.category.CategoryDto;
import lombok.Data;

import java.util.Date;

@Data
public class CourseDto {
    private Long id;
    private String courseName;
    private String thumbnail;
    private Integer state;
    private Date startDate;
    private AccountDto teacherInfo;
    private CategoryDto categoryInfo;
}
