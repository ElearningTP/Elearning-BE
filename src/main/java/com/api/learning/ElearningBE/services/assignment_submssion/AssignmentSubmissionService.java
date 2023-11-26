package com.api.learning.ElearningBE.services.assignment_submssion;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.form.assignment_submission.CreateAssignmentSubmissionForm;
import com.api.learning.ElearningBE.form.assignment_submission.UpdateAssignmentSubmissionForm;

public interface AssignmentSubmissionService {
    ApiMessageDto<String> submit(CreateAssignmentSubmissionForm createAssignmentSubmissionForm);
    ApiMessageDto<String> update(UpdateAssignmentSubmissionForm updateAssignmentSubmissionForm);
    ApiMessageDto<String> delete(Long id);
}
