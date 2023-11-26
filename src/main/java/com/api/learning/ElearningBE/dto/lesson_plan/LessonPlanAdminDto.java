package com.api.learning.ElearningBE.dto.lesson_plan;

import com.api.learning.ElearningBE.dto.BaseAdminDto;
import com.api.learning.ElearningBE.dto.account.AccountDto;
import com.api.learning.ElearningBE.dto.modules.ModulesAdminDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class LessonPlanAdminDto extends BaseAdminDto {
    private String name;
    private String description;
    private AccountDto teacherInfo;
    private List<ModulesAdminDto> modulesInfo;
}
