package com.api.learning.ElearningBE.services.lesson_plan;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.lesson_plan.LessonPlanAdminDto;
import com.api.learning.ElearningBE.dto.lesson_plan.LessonPlanDto;
import com.api.learning.ElearningBE.form.lesson_plan.CreateLessonPlanForm;
import com.api.learning.ElearningBE.form.lesson_plan.UpdateLessonPlanForm;
import com.api.learning.ElearningBE.storage.criteria.LessonPlanCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LessonPlanService {
    ApiMessageDto<ResponseListDto<List<LessonPlanDto>>> list(LessonPlanCriteria lessonPlanCriteria, Pageable pageable);
    ApiMessageDto<LessonPlanAdminDto> retrieve(Long id);
    ApiMessageDto<String> create(CreateLessonPlanForm createLessonPlanForm);
    ApiMessageDto<String> update(UpdateLessonPlanForm updateLessonPlanForm);
    ApiMessageDto<String> delete(Long id);
}
