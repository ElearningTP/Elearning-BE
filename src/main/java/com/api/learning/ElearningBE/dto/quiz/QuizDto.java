package com.api.learning.ElearningBE.dto.quiz;

import com.api.learning.ElearningBE.dto.modules.ModulesDto;
import com.api.learning.ElearningBE.dto.quiz_submission.QuizSubmissionDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QuizDto {
    private Long id;
    private String quizTitle;
    private String description;
    private Long quizTimeLimit;
    private Date startDate;
    private Date endDate;
    private Integer attemptNumber;
    private ModulesDto modulesInfo;
    private List<QuizSubmissionDto> quizSubmissionInfo;
}
