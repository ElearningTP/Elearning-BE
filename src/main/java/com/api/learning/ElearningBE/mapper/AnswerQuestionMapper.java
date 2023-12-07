package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.answer_question.AnswerQuestionAdminDto;
import com.api.learning.ElearningBE.dto.answer_question.AnswerQuestionDto;
import com.api.learning.ElearningBE.dto.answer_question.ReviewAnswerQuestionDto;
import com.api.learning.ElearningBE.dto.answer_question.StartQuizAnswerQuestionDto;
import com.api.learning.ElearningBE.form.answer_question.CreateAnswerQuestionForm;
import com.api.learning.ElearningBE.form.answer_question.UpdateAnswerQuestionForm;
import com.api.learning.ElearningBE.storage.entities.AnswerQuestion;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {QuizQuestionMapper.class})
public interface AnswerQuestionMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "answerContent", target = "content")
    @Mapping(source = "isCorrect", target = "isCorrect")
    AnswerQuestion fromCreateAnswerQuestionFormToEntity(CreateAnswerQuestionForm createAnswerQuestionForm);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "answerContent", target = "content")
    @Mapping(source = "isCorrect", target = "isCorrect")
    void fromUpdateAnswerQuestionFormToEntity(UpdateAnswerQuestionForm updateAnswerQuestionForm, @MappingTarget AnswerQuestion answerQuestion);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "content", target = "answerContent")
    @Mapping(source = "isCorrect", target = "isCorrect")
    @Mapping(source = "question", target = "questionInfo", qualifiedByName = "fromEntityToQuizQuestionDto")
    @Named("fromEntityToAnswerQuestionDto")
    AnswerQuestionDto fromEntityToAnswerQuestionDto(AnswerQuestion answerQuestion);
    @IterableMapping(elementTargetType = AnswerQuestionDto.class, qualifiedByName = "fromEntityToAnswerQuestionDto")
    List<AnswerQuestionDto> fromEntityToAnswerQuestionDtoList(List<AnswerQuestion> answerQuestions);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "content", target = "answerContent")
    @Mapping(source = "isCorrect", target = "isCorrect")
    @Mapping(source = "question", target = "questionInfo", qualifiedByName = "fromEntityToQuizQuestionDto")
    AnswerQuestionAdminDto fromEntityToAnswerQuestionAdminDto(AnswerQuestion answerQuestion);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "content", target = "answerContent")
    @Named("fromEntityToStartQuizAnswerQuestionDto")
    StartQuizAnswerQuestionDto fromEntityToStartQuizAnswerQuestionDto(AnswerQuestion answerQuestion);
    @IterableMapping(elementTargetType = StartQuizAnswerQuestionDto.class, qualifiedByName = "fromEntityToStartQuizAnswerQuestionDto")
    List<StartQuizAnswerQuestionDto> fromEntityToStartQuizAnswerQuestionDtoList(List<AnswerQuestion> answerQuestions);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "content", target = "answerContent")
    @Mapping(source = "isCorrect", target = "isCorrect")
    @Named("fromEntityToReviewAnswerQuestionDto")
    ReviewAnswerQuestionDto fromEntityToReviewAnswerQuestionDto(AnswerQuestion answerQuestion);
    @IterableMapping(elementTargetType = ReviewAnswerQuestionDto.class, qualifiedByName = "fromEntityToReviewAnswerQuestionDto")
    List<ReviewAnswerQuestionDto> fromEntityToReviewAnswerQuestionDtoList(List<AnswerQuestion> answerQuestions);
}
