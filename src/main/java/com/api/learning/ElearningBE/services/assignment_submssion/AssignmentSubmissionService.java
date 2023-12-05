package com.api.learning.ElearningBE.services.assignment_submssion;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.assignment_submission.AssignmentSubmissionAdminDto;
import com.api.learning.ElearningBE.dto.assignment_submission.AssignmentSubmissionDto;
import com.api.learning.ElearningBE.form.assignment_submission.CreateAssignmentSubmissionForm;
import com.api.learning.ElearningBE.form.assignment_submission.UpdateAssignmentSubmissionForm;
import com.api.learning.ElearningBE.storage.criteria.AssignmentSubmissionCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssignmentSubmissionService {
    ApiMessageDto<ResponseListDto<List<AssignmentSubmissionAdminDto>>> list(AssignmentSubmissionCriteria assignmentSubmissionCriteria, Pageable pageable);
    ApiMessageDto<AssignmentSubmissionAdminDto> retrieve(Long id);
    ApiMessageDto<AssignmentSubmissionDto> submit(CreateAssignmentSubmissionForm createAssignmentSubmissionForm);
    ApiMessageDto<AssignmentSubmissionDto> update(UpdateAssignmentSubmissionForm updateAssignmentSubmissionForm);
    ApiMessageDto<String> delete(Long id);
}
