package com.api.learning.ElearningBE.dto.assignment_submission;

import com.api.learning.ElearningBE.dto.assignment.AssignmentDto;
import lombok.Data;

import java.util.Date;

@Data
public class AssignmentSubmissionDto {
    private Long id;
    private Double score;
    private AssignmentDto assignmentInfo;
}
