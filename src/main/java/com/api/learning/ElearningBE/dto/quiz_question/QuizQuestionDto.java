package com.api.learning.ElearningBE.dto.quiz_question;

import com.api.learning.ElearningBE.dto.answer_question.AnswerQuestionDto;
import com.api.learning.ElearningBE.dto.quiz.QuizDto;
import com.api.learning.ElearningBE.storage.entities.AnswerQuestion;
import lombok.Data;

import java.util.List;

@Data
public class QuizQuestionDto {
    private Long id;
    private String questionContent;
    private Double score;
    private Integer questionType;
    private QuizDto quizInfo;
    private List<AnswerQuestionDto> answers;
}
