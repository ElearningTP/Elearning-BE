package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.course.CourseAdminDto;
import com.api.learning.ElearningBE.dto.course.CourseDto;
import com.api.learning.ElearningBE.form.course.CreateCourseForm;
import com.api.learning.ElearningBE.storage.entities.Course;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class, CategoryMapper.class, LessonPlanMapper.class})
public interface CourseMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "courseName", target = "name")
    @Mapping(source = "thumbnail", target = "thumbnail")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "startDate", target = "startDate")
    Course fromCreateCourseFormToEntity(CreateCourseForm createCourseForm);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "courseName")
    @Mapping(source = "thumbnail", target = "thumbnail")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "teacher", target = "teacherInfo", qualifiedByName = "fromEntityToAccountDtoAutoComplete")
    @Mapping(source = "category", target = "categoryId", qualifiedByName = "fromEntityToCategoryDtoAutoComplete")
    @Named("fromEntityToCourseDtoAutoComplete")
    CourseDto fromEntityToCourseDtoAutoComplete(Course course);
    @IterableMapping(elementTargetType = CourseDto.class, qualifiedByName = "fromEntityToCourseDtoAutoComplete")
    List<CourseDto> fromEntityToCourseDtoAutoCompleteList(List<Course> courses);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "courseName")
    @Mapping(source = "thumbnail", target = "thumbnail")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "teacher", target = "teacherInfo", qualifiedByName = "fromEntityToAccountAdminDto")
    @Mapping(source = "lessonPlan", target = "lessonPlanInfo", qualifiedByName = "fromEntityToLessonPlanAdminDto")
    @Mapping(source = "category", target = "categoryId", qualifiedByName = "fromEntityToCategoryAdminDtoForGet")
    @Named("fromEntityToCourseAdminDto")
    CourseAdminDto fromEntityToCourseAdminDto(Course course);

    @IterableMapping(elementTargetType = CourseAdminDto.class, qualifiedByName = "fromEntityToCourseAdminDto")
    List<CourseAdminDto> fromEntityToCourseAdminDtoList(List<Course> courses);
}