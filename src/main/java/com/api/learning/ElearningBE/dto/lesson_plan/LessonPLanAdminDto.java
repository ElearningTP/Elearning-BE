package com.api.learning.ElearningBE.dto.lesson_plan;

import com.api.learning.ElearningBE.dto.BaseAdminDto;
import com.api.learning.ElearningBE.dto.account.AccountAdminDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LessonPLanAdminDto extends BaseAdminDto {
    private String name;
    private String description;
    private AccountAdminDto teacherInfo;
}
