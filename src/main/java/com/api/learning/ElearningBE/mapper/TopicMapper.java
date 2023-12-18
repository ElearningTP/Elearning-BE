package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.topic.TopicAdminDto;
import com.api.learning.ElearningBE.dto.topic.TopicDto;
import com.api.learning.ElearningBE.form.topic.CreateTopicForm;
import com.api.learning.ElearningBE.form.topic.UpdateTopicForm;
import com.api.learning.ElearningBE.storage.entities.Topic;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class, ForumMapper.class})
public interface TopicMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "topicContent", target = "content")
    Topic fromCreateTopicFormToEntity(CreateTopicForm createTopicForm);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "topicContent", target = "content")
    void fromUpdateTopicFormToEntity(UpdateTopicForm updateTopicForm, @MappingTarget Topic topic);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "content", target = "topicContent")
    @Mapping(source = "forum", target = "forumInfo", qualifiedByName = "fromEntityToForumDto")
    @Mapping(source = "account", target = "accountInfo", qualifiedByName = "fromEntityToAccountDtoAutoComplete")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Named("fromEntityToTopicDto")
    TopicDto fromEntityToTopicDto(Topic topic);
    @IterableMapping(elementTargetType = TopicDto.class, qualifiedByName = "fromEntityToTopicDto")
    List<TopicDto> fromEntityToTopicDtoList(List<Topic> topics);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "content", target = "topicContent")
    @Mapping(source = "status",target = "status")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "forum", target = "forumInfo", qualifiedByName = "fromEntityToForumDto")
    @Mapping(source = "account", target = "accountInfo", qualifiedByName = "fromEntityToAccountDtoAutoComplete")
    TopicAdminDto fromEntityToTopicAdminDto(Topic topic);
}
