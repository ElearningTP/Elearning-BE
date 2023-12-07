package com.api.learning.ElearningBE.dto.quiz_submission;

import lombok.Data;

import java.util.Date;

@Data
public class QuizSubmissionDto {
    private Long id;
    private Double score;
    private Long totalTime;
    private Date createDate;
    private Date modifiedDate;
}
