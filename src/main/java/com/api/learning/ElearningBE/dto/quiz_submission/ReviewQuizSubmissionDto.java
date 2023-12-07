package com.api.learning.ElearningBE.dto.quiz_submission;

import com.api.learning.ElearningBE.dto.quiz_question.ReviewQuizQuestionDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ReviewQuizSubmissionDto {
    private Long id;
    private Double score;
    private Long totalTime;
    private Date createDate;
    private Date modifiedDate;
    private List<ReviewQuizQuestionDto> questions = new ArrayList<>();
}
