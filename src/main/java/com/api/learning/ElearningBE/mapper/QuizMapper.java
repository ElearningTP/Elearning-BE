package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.quiz.QuizAdminDto;
import com.api.learning.ElearningBE.dto.quiz.QuizDto;
import com.api.learning.ElearningBE.form.quiz.CreateQuizForm;
import com.api.learning.ElearningBE.form.quiz.UpdateQuizForm;
import com.api.learning.ElearningBE.storage.entities.Quiz;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ModulesMapper.class})
public interface QuizMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "quizTitle", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "quizTimeLimit", target = "timeLimit")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    Quiz fromCreateQuizFormToEntity(CreateQuizForm createQuizForm);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "quizTitle", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "quizTimeLimit", target = "timeLimit")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    void fromUpdateQuizFormToEntity(UpdateQuizForm updateQuizForm, @MappingTarget Quiz quiz);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "quizTitle")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "timeLimit", target = "quizTimeLimit")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "modules", target = "modulesInfo", qualifiedByName = "fromEntityToModulesDto")
    @Named("fromEntityToQuizDto")
    QuizDto fromEntityToQuizDto(Quiz quiz);
    @IterableMapping(elementTargetType = QuizDto.class, qualifiedByName = "fromEntityToQuizDto")
    List<QuizDto> fromEntityToQuizDtoList(List<Quiz> quizzes);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "quizTitle")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "timeLimit", target = "quizTimeLimit")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "modules", target = "modulesInfo", qualifiedByName = "fromEntityToModulesDto")
    @Named("fromEntityToQuizAdminDto")
    QuizAdminDto fromEntityToQuizAdminDto(Quiz quiz);
}
