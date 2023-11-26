package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.lesson_plan.LessonPlanAdminDto;
import com.api.learning.ElearningBE.dto.lesson_plan.LessonPlanDto;
import com.api.learning.ElearningBE.form.lesson_plan.CreateLessonPlanForm;
import com.api.learning.ElearningBE.form.lesson_plan.UpdateLessonPlanForm;
import com.api.learning.ElearningBE.storage.entities.LessonPlan;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class, ModulesMapper.class})
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

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "teacher", target = "teacherInfo", qualifiedByName = "fromEntityToAccountDtoAutoComplete")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Named("fromEntityToLessonPlanAdminDto")
    LessonPlanAdminDto fromEntityToLessonPlanAdminDto(LessonPlan lessonPlan);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "teacher", target = "teacherInfo", qualifiedByName = "fromEntityToAccountDtoAutoComplete")
    @Named("fromEntityToLessonPlanDto")
    LessonPlanDto fromEntityToLessonPlanDto(LessonPlan lessonPlan);

    @IterableMapping(elementTargetType = LessonPlanDto.class, qualifiedByName = "fromEntityToLessonPlanDto")
    List<LessonPlanDto> fromEntityToLessonPlanDtoList(List<LessonPlan> lessonPlans);
}
