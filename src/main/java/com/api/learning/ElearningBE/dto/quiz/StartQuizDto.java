package com.api.learning.ElearningBE.dto.quiz;

import com.api.learning.ElearningBE.dto.quiz_question.StartQuizQuestionDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class StartQuizDto {
    private Long id;
    private String quizTitle;
    private String description;
    private Long quizTimeLimit;
    private Date startDate;
    private Date endDate;
    private Integer attemptNumber;
    private List<StartQuizQuestionDto> questions;
}
