package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.quiz_question.QuizQuestionAdminDto;
import com.api.learning.ElearningBE.dto.quiz_question.QuizQuestionDto;
import com.api.learning.ElearningBE.dto.quiz_question.ReviewQuizQuestionDto;
import com.api.learning.ElearningBE.dto.quiz_question.StartQuizQuestionDto;
import com.api.learning.ElearningBE.form.quiz_question.CreateQuizQuestionForm;
import com.api.learning.ElearningBE.form.quiz_question.UpdateQuizQuestionForm;
import com.api.learning.ElearningBE.storage.entities.QuizQuestion;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {QuizMapper.class})
public interface QuizQuestionMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "questionContent", target = "content")
    @Mapping(source = "questionType", target = "questionType")
    QuizQuestion fromCreateQuizQuestionFormToEntity(CreateQuizQuestionForm createQuizQuestionForm);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "questionContent", target = "content")
    @Mapping(source = "questionType", target = "questionType")
    void fromUpdateQuizQuestionFormToEntity(UpdateQuizQuestionForm updateQuizQuestionForm, @MappingTarget QuizQuestion question);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "content", target = "questionContent")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "questionType", target = "questionType")
    @Mapping(source = "quiz", target = "quizInfo", qualifiedByName = "fromEntityToQuizDto")
    @Named("fromEntityToQuizQuestionDto")
    QuizQuestionDto fromEntityToQuizQuestionDto(QuizQuestion question);
    @IterableMapping(elementTargetType = QuizQuestionDto.class, qualifiedByName = "fromEntityToQuizQuestionDto")
    List<QuizQuestionDto> fromEntityToQuizQuestionDtoList(List<QuizQuestion> quizQuestions);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "content", target = "questionContent")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "questionType", target = "questionType")
    @Mapping(source = "quiz", target = "quizInfo", qualifiedByName = "fromEntityToQuizDto")
    QuizQuestionAdminDto fromEntityToQuizQuestionAdminDto(QuizQuestion question);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "content", target = "questionContent")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "questionType", target = "questionType")
    @Named("fromEntityToStartQuizQuestionDto")
    StartQuizQuestionDto fromEntityToStartQuizQuestionDto(QuizQuestion question);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "content", target = "questionContent")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "questionType", target = "questionType")
    @Named("fromEntityToReviewQuizQuestionDto")
    ReviewQuizQuestionDto fromEntityToReviewQuizQuestionDto(QuizQuestion question);
    @IterableMapping(elementTargetType = ReviewQuizQuestionDto.class, qualifiedByName = "fromEntityToReviewQuizQuestionDto")
    List<ReviewQuizQuestionDto> fromEntityToReviewQuizQuestionDtoList(List<QuizQuestion> questions);

}
