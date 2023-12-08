package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.quiz_submission.QuizSubmissionDto;
import com.api.learning.ElearningBE.dto.quiz_submission.ReviewQuizSubmissionDto;
import com.api.learning.ElearningBE.storage.entities.QuizSubmission;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface QuizSubmissionMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "totalTime", target = "totalTime")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    ReviewQuizSubmissionDto fromEntityToReviewQuizSubmissionDto(QuizSubmission quizSubmission);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "totalTime", target = "totalTime")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Named("fromEntityToQuizSubmissionDto")
    QuizSubmissionDto fromEntityToQuizSubmissionDto(QuizSubmission quizSubmission);
    @IterableMapping(elementTargetType = QuizSubmissionDto.class, qualifiedByName = "fromEntityToQuizSubmissionDto")
    List<QuizSubmissionDto> fromEntityToQuizSubmissionDtoList(List<QuizSubmission> quizSubmissions);



}
