package com.api.learning.ElearningBE.dto.quiz_question;

import com.api.learning.ElearningBE.dto.answer_question.AnswerQuestionDto;
import com.api.learning.ElearningBE.dto.quiz.QuizDto;
import lombok.Data;

@Data
public class QuizQuestionDto {
    private Long id;
    private String questionContent;
    private Double score;
    private Integer questionType;
    private QuizDto quizInfo;
}
