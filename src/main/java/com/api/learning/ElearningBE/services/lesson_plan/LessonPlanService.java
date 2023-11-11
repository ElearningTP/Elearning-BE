package com.api.learning.ElearningBE.services.lesson_plan;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.form.lesson_plan.CreateLessonPlanForm;
import com.api.learning.ElearningBE.form.lesson_plan.UpdateLessonPlanForm;

public interface LessonPlanService {
    ApiMessageDto<String> create(CreateLessonPlanForm createLessonPlanForm);
    ApiMessageDto<String> update(UpdateLessonPlanForm updateLessonPlanForm);
    ApiMessageDto<String> delete(Long id);
}
