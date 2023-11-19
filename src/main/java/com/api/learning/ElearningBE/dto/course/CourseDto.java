package com.api.learning.ElearningBE.dto.course;

import com.api.learning.ElearningBE.dto.account.AccountDto;
import com.api.learning.ElearningBE.dto.category.CategoryDto;
import com.api.learning.ElearningBE.dto.lesson_plan.LessonPlanDto;
import com.api.learning.ElearningBE.dto.modules.ModulesDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CourseDto {
    private Long id;
    private String courseName;
    private String thumbnail;
    private Integer state;
    private Date startDate;
    private String description;
    private AccountDto teacherInfo;
    private LessonPlanDto lessonPlanInfo;
    private CategoryDto categoryInfo;
    private List<ModulesDto> modulesInfo;
}
