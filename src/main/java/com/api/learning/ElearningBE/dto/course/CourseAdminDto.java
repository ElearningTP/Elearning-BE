package com.api.learning.ElearningBE.dto.course;

import com.api.learning.ElearningBE.dto.BaseAdminDto;
import com.api.learning.ElearningBE.dto.account.AccountDto;
import com.api.learning.ElearningBE.dto.category.CategoryAdminDto;
import com.api.learning.ElearningBE.dto.lesson_plan.LessonPLanAdminDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CourseAdminDto extends BaseAdminDto {
    private String courseName;
    private String thumbnail;
    private Integer state;
    private Date startDate;
    private List<String> requirements;
    private List<String> objectives;
    private String description;
    private AccountDto teacherInfo;
    private LessonPLanAdminDto lessonPlanInfo;
    private CategoryAdminDto categoryInfo;
}
