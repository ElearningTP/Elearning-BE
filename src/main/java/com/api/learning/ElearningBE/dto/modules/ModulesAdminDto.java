package com.api.learning.ElearningBE.dto.modules;

import com.api.learning.ElearningBE.dto.BaseAdminDto;
import com.api.learning.ElearningBE.dto.lesson_plan.LessonPlanAdminDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ModulesAdminDto extends BaseAdminDto {
    private String modulesName;
    private String description;
    private LessonPlanAdminDto lessonPlanInfo;
}
