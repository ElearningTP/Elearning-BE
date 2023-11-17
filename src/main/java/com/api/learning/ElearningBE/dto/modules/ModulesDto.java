package com.api.learning.ElearningBE.dto.modules;

import com.api.learning.ElearningBE.dto.lesson_plan.LessonPlanDto;
import lombok.Data;

@Data
public class ModulesDto {
    private Long id;
    private String modulesName;
    private String description;
    private LessonPlanDto lessonPlanInfo;
}
