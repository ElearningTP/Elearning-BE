package com.api.learning.ElearningBE.dto.quiz_submission;

import com.api.learning.ElearningBE.dto.account.AccountDto;
import lombok.Data;

import java.util.Date;

@Data
public class QuizSubmissionDto {
    private Long id;
    private Double score;
    private Long totalTime;
    private Date createDate;
    private Date modifiedDate;
    private AccountDto studentInfo;
}
