package com.api.learning.ElearningBE.dto.assignment_submission;

import com.api.learning.ElearningBE.dto.BaseAdminDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class AssignmentSubmissionAdminDto extends BaseAdminDto {
    private Date submissionDate;
    private String textSubmission;
    private String fileSubmissionUrl;
    private Double score;
}
