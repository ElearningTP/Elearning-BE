package com.api.learning.ElearningBE.dto.lesson_plan;

import com.api.learning.ElearningBE.dto.account.AccountDto;
import lombok.Data;

@Data
public class LessonPlanDto {
    private Long id;
    private String name;
    private String description;
    private AccountDto teacherInfo;
}
