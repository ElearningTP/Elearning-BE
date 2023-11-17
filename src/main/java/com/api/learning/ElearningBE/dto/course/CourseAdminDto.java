package com.api.learning.ElearningBE.dto.course;

import com.api.learning.ElearningBE.dto.BaseAdminDto;
import com.api.learning.ElearningBE.dto.account.AccountAdminDto;
import com.api.learning.ElearningBE.dto.category.CategoryAdminDto;
import com.api.learning.ElearningBE.dto.lesson_plan.LessonPLanAdminDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class CourseAdminDto extends BaseAdminDto {
    private String courseName;
    private String thumbnail;
    private Integer state;
    private Date startDate;
    private AccountAdminDto teacherInfo;
    private LessonPLanAdminDto lessonPlanInfo;
    private CategoryAdminDto categoryInfo;
}
