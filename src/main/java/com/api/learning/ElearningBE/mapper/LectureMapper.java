package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.lecture.LectureAdminDto;
import com.api.learning.ElearningBE.dto.lecture.LectureDto;
import com.api.learning.ElearningBE.form.lecture.CreateLectureForm;
import com.api.learning.ElearningBE.form.lecture.UpdateLectureForm;
import com.api.learning.ElearningBE.storage.entities.Lecture;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ModulesMapper.class})
public interface LectureMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "lectureName", target = "name")
    @Mapping(source = "lectureContent", target = "content")
    Lecture fromCreateLectureToFormToEntity(CreateLectureForm createLectureForm);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "lectureName", target = "name")
    @Mapping(source = "lectureContent", target = "content")
    void fromUpdateLectureFormToEntity(UpdateLectureForm updateLectureForm, @MappingTarget Lecture lecture);
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "lectureName")
    @Mapping(source = "content", target = "lectureContent")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "modules", target = "modulesInfo", qualifiedByName = "fromEntityToModulesDto")
    LectureAdminDto fromEntityToLectureAdminDto(Lecture lecture);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "lectureName")
    @Mapping(source = "content", target = "lectureContent")
    @Mapping(source = "modules", target = "modulesInfo", qualifiedByName = "fromEntityToModulesDto")
    @Named("fromEntityToLectureDto")
    LectureDto fromEntityToLectureDto(Lecture lecture);

    @IterableMapping(elementTargetType = LectureDto.class, qualifiedByName = "fromEntityToLectureDto")
    List<LectureDto> fromEntityToLectureDtoList(List<Lecture> lectures);
}
