package com.api.learning.ElearningBE.services.topic;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.topic.TopicAdminDto;
import com.api.learning.ElearningBE.dto.topic.TopicDto;
import com.api.learning.ElearningBE.form.topic.CreateTopicForm;
import com.api.learning.ElearningBE.form.topic.UpdateTopicForm;
import com.api.learning.ElearningBE.storage.criteria.TopicCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TopicService {
    ApiMessageDto<ResponseListDto<List<TopicDto>>> list(TopicCriteria topicCriteria, Pageable pageable);
    ApiMessageDto<TopicAdminDto> retrieve(Long id);
    ApiMessageDto<TopicDto> create(CreateTopicForm createTopicForm);
    ApiMessageDto<TopicDto> update(UpdateTopicForm updateTopicForm);
    ApiMessageDto<String> delete(Long id);
}
