package com.api.learning.ElearningBE.dto.answer_question;

import lombok.Data;

@Data
public class ReviewAnswerQuestionDto {
    private Long id;
    private String answerContent;
    private Boolean isCorrect;
    private Boolean isSelected;
}
