package com.api.learning.ElearningBE.dto.quiz_question;

import com.api.learning.ElearningBE.dto.answer_question.ReviewAnswerQuestionDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReviewQuizQuestionDto {
    private Long id;
    private String questionContent;
    private Double score;
    private Integer questionType;
    List<ReviewAnswerQuestionDto> answers = new ArrayList<>();
}
