package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.form.student.CreateStudentForm;
import com.api.learning.ElearningBE.storage.entities.Student;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "nameStudent", target = "name")
    @Mapping(source = "emailStudent", target = "email")
//    @Mapping(source = )
    Student fromCreateStudentForm(CreateStudentForm createStudentForm);

}
