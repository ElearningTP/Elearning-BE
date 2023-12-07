package com.api.learning.ElearningBE.dto.quiz_question;

import com.api.learning.ElearningBE.dto.BaseAdminDto;
import com.api.learning.ElearningBE.dto.quiz.QuizDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuizQuestionAdminDto extends BaseAdminDto {
    private String questionContent;
    private Double score;
    private Integer questionType;
    private QuizDto quizInfo;
}
