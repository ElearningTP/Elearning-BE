package com.api.learning.ElearningBE.dto.answer_question;

import com.api.learning.ElearningBE.dto.quiz_question.QuizQuestionDto;
import lombok.Data;

@Data
public class AnswerQuestionDto {
    private Long id;
    private String answerContent;
    private Boolean isCorrect;
    private QuizQuestionDto questionInfo;
}
