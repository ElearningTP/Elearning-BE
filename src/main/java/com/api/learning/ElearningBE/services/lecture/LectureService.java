package com.api.learning.ElearningBE.services.lecture;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.lecture.LectureAdminDto;
import com.api.learning.ElearningBE.dto.lecture.LectureDto;
import com.api.learning.ElearningBE.form.lecture.CreateLectureForm;
import com.api.learning.ElearningBE.form.lecture.UpdateLectureForm;
import com.api.learning.ElearningBE.storage.criteria.LectureCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LectureService {
    ApiMessageDto<ResponseListDto<List<LectureDto>>> list(LectureCriteria lectureCriteria, Pageable pageable);
    ApiMessageDto<LectureAdminDto> retrieve(Long id);
    ApiMessageDto<String> create(CreateLectureForm createLectureForm);
    ApiMessageDto<String> update(UpdateLectureForm updateLectureForm);
    ApiMessageDto<String> delete(Long id);
}
