package com.api.learning.ElearningBE.dto.quiz;

import com.api.learning.ElearningBE.dto.BaseAdminDto;
import com.api.learning.ElearningBE.dto.modules.ModulesDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuizAdminDto extends BaseAdminDto {
    private String quizTitle;
    private String description;
    private Long quizTimeLimit;
    private Date startDate;
    private Date endDate;
    private ModulesDto modulesInfo;
}
