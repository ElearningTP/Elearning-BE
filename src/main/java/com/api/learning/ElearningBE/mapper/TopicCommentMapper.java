package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.topic_comment.TopicCommentAdminDto;
import com.api.learning.ElearningBE.dto.topic_comment.TopicCommentDto;
import com.api.learning.ElearningBE.form.topic_comment.CreateTopicCommentForm;
import com.api.learning.ElearningBE.form.topic_comment.UpdateTopicCommentForm;
import com.api.learning.ElearningBE.storage.entities.TopicComment;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {TopicMapper.class, AccountMapper.class})
public interface TopicCommentMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "content", target = "content")
    TopicComment fromCreateTopicCommentFormToEntity(CreateTopicCommentForm createTopicCommentForm);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "content", target = "content")
    void fromUpdateTopicCommentFormToEntity(UpdateTopicCommentForm updateTopicCommentForm, @MappingTarget TopicComment topicComment);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "topic", target = "topicInfo", qualifiedByName = "fromEntityToTopicDto")
    @Mapping(source = "account", target = "accountInfo", qualifiedByName = "fromEntityToAccountDtoAutoComplete")
    @Named("fromEntityToTopicCommentAdminDto")
    TopicCommentAdminDto fromEntityToTopicCommentAdminDto(TopicComment topicComment);
    @IterableMapping(elementTargetType = TopicCommentAdminDto.class, qualifiedByName = "fromEntityToTopicCommentAdminDto")
    List<TopicCommentAdminDto> fromEntityToTopicCommentAdminDto(List<TopicComment> topicComments);
}
