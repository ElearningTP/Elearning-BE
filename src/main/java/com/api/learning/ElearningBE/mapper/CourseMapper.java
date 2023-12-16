package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.course.CourseAdminDto;
import com.api.learning.ElearningBE.dto.course.CourseDto;
import com.api.learning.ElearningBE.form.course.CreateCourseForm;
import com.api.learning.ElearningBE.form.course.UpdateCourseForm;
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
    @Mapping(source = "requirements", target = "requirements")
    @Mapping(source = "objectives", target = "objectives")
    @Mapping(source = "description", target = "description")
    Course fromCreateCourseFormToEntity(CreateCourseForm createCourseForm);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "courseName", target = "name")
    @Mapping(source = "thumbnail", target = "thumbnail")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "requirements", target = "requirements")
    @Mapping(source = "objectives", target = "objectives")
    @Mapping(source = "description", target = "description")
    void fromUpdateCourseFormToEntity(UpdateCourseForm updateCourseForm, @MappingTarget Course course);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "courseName")
    @Mapping(source = "thumbnail", target = "thumbnail")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "teacher", target = "teacherInfo", qualifiedByName = "fromEntityToAccountDtoAutoComplete")
    @Mapping(source = "category", target = "categoryInfo", qualifiedByName = "fromEntityToCategoryDtoAutoComplete")
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
    @Mapping(source = "requirements", target = "requirements")
    @Mapping(source = "objectives", target = "objectives")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "teacher", target = "teacherInfo", qualifiedByName = "fromEntityToAccountDtoAutoComplete")
    @Mapping(source = "lessonPlan", target = "lessonPlanInfo", qualifiedByName = "fromEntityToLessonPlanAdminDto")
    @Mapping(source = "category", target = "categoryInfo", qualifiedByName = "fromEntityToCategoryAdminDtoForGet")
    @Named("fromEntityToCourseAdminDto")
    CourseAdminDto fromEntityToCourseAdminDto(Course course);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "courseName")
    @Mapping(source = "thumbnail", target = "thumbnail")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "teacher", target = "teacherInfo", qualifiedByName = "fromEntityToAccountDtoAutoComplete")
    @Mapping(source = "lessonPlan", target = "lessonPlanInfo", qualifiedByName = "fromEntityToLessonPlanDto")
    @Mapping(source = "category", target = "categoryInfo", qualifiedByName = "fromEntityToCategoryDtoAutoComplete")
    @Named("fromEntityToCourseDto")
    CourseDto fromEntityToCourseDto(Course course);

    @IterableMapping(elementTargetType = CourseDto.class, qualifiedByName = "fromEntityToCourseDto")
    List<CourseDto> fromEntityToCourseDtoList(List<Course> courses);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "courseName")
    @Mapping(source = "thumbnail", target = "thumbnail")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "teacher", target = "teacherInfo", qualifiedByName = "fromEntityToAccountDtoAutoComplete")
    CourseDto fromEntityToCourseDtoForMySchedule(Course course);
}
