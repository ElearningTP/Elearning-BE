package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.form.lesson_plan.CreateLessonPlanForm;
import com.api.learning.ElearningBE.form.lesson_plan.UpdateLessonPlanForm;
import com.api.learning.ElearningBE.storage.entities.LessonPlan;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LessonPlanMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    LessonPlan fromCreateLessonPlanFormToEntity(CreateLessonPlanForm createLessonPlanForm);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "status", target = "status")
    void fromUpdateLessonPlanFormToEntity(UpdateLessonPlanForm updateLessonPlanForm, @MappingTarget LessonPlan lessonPlan);
}
