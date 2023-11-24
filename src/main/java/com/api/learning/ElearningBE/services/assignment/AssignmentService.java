package com.api.learning.ElearningBE.services.assignment;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.assignment.AssignmentAdminDto;
import com.api.learning.ElearningBE.dto.assignment.AssignmentDto;
import com.api.learning.ElearningBE.form.assignment.CreateAssignmentForm;
import com.api.learning.ElearningBE.form.assignment.UpdateAssignmentForm;
import com.api.learning.ElearningBE.storage.criteria.AssignmentCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssignmentService {
    ApiMessageDto<ResponseListDto<List<AssignmentDto>>> list(AssignmentCriteria assignmentCriteria, Pageable pageable);
    ApiMessageDto<AssignmentAdminDto> retrieve(Long id);
    ApiMessageDto<String> create(CreateAssignmentForm createAssignmentForm);
    ApiMessageDto<String> update(UpdateAssignmentForm updateAssignmentForm);
    ApiMessageDto<String> delete(Long id);
}
