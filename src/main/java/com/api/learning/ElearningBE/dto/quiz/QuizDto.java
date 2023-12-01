package com.api.learning.ElearningBE.dto.quiz;

import com.api.learning.ElearningBE.dto.modules.ModulesDto;
import lombok.Data;

import java.util.Date;

@Data
public class QuizDto {
    private Long id;
    private String quizTitle;
    private String description;
    private Long quizTimeLimit;
    private Date startDate;
    private Date endDate;
    private ModulesDto modulesInfo;
}
