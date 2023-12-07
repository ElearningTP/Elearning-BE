package com.api.learning.ElearningBE.dto.answer_question;

import com.api.learning.ElearningBE.dto.BaseAdminDto;
import com.api.learning.ElearningBE.dto.quiz_question.QuizQuestionDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AnswerQuestionAdminDto extends BaseAdminDto {
    private String answerContent;
    private Boolean isCorrect;
    private QuizQuestionDto questionInfo;
}
