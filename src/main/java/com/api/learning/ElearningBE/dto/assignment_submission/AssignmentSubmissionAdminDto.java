package com.api.learning.ElearningBE.dto.assignment_submission;

import com.api.learning.ElearningBE.dto.BaseAdminDto;
import com.api.learning.ElearningBE.dto.account.AccountDto;
import com.api.learning.ElearningBE.dto.assignment.AssignmentDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class AssignmentSubmissionAdminDto extends BaseAdminDto {
    private String textSubmission;
    private String fileSubmissionUrl;
    private String linkSubmission;
    private Double score;
    private AssignmentDto assignmentInfo;
    private AccountDto studentInfo;
}
