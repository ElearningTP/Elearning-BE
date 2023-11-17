package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.course_registration.CourseRegistrationDto;
import com.api.learning.ElearningBE.storage.entities.CourseRegistration;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class, CourseMapper.class})
public interface CourseRegistrationMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "student", target = "studentInfo", qualifiedByName = "fromEntityToAccountDtoAutoComplete")
    @Mapping(source = "course", target = "courseInfo", qualifiedByName = "fromEntityToCourseDtoAutoComplete")
    @Mapping(source = "status", target = "status")
    @Named("fromEntityToCourseRegistrationDto")
    CourseRegistrationDto fromEntityToCourseRegistrationDto(CourseRegistration courseRegistration);
    @IterableMapping(elementTargetType = CourseRegistrationDto.class, qualifiedByName = "fromEntityToCourseRegistrationDto")
    List<CourseRegistrationDto>fromEntityToCourseRegistrationDtoList(List<CourseRegistration> courseRegistrations);
}
