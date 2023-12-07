package com.api.learning.ElearningBE.dto.quiz_question;

import com.api.learning.ElearningBE.dto.answer_question.StartQuizAnswerQuestionDto;
import lombok.Data;

import java.util.List;

@Data
public class StartQuizQuestionDto {
    private Long id;
    private String questionContent;
    private Double score;
    private Integer questionType;
    private List<StartQuizAnswerQuestionDto> answers;
}
