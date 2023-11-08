package com.api.learning.ElearningBE.services.subject;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.subject.SubjectAdminDto;
import com.api.learning.ElearningBE.form.subject.CreateSubjectForm;
import com.api.learning.ElearningBE.form.subject.UpdateSubjectForm;
import com.api.learning.ElearningBE.storage.criteria.SubjectCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubjectService {
    ApiMessageDto<ResponseListDto<List<SubjectAdminDto>>> list(SubjectCriteria subjectCriteria, Pageable pageable);
    ApiMessageDto<SubjectAdminDto> retrieve(Long id);
    ApiMessageDto<String> create(CreateSubjectForm createSubjectForm);
    ApiMessageDto<String> update(UpdateSubjectForm updateSubjectForm);
    ApiMessageDto<String> delete(Long id);
}
