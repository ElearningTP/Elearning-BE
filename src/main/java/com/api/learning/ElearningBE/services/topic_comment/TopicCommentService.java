package com.api.learning.ElearningBE.services.topic_comment;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.topic_comment.TopicCommentAdminDto;
import com.api.learning.ElearningBE.form.topic_comment.CreateTopicCommentForm;
import com.api.learning.ElearningBE.form.topic_comment.UpdateTopicCommentForm;
import com.api.learning.ElearningBE.storage.criteria.TopicCommentCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TopicCommentService {
    ApiMessageDto<ResponseListDto<List<TopicCommentAdminDto>>> list(TopicCommentCriteria topicCommentCriteria, Pageable pageable);
    ApiMessageDto<TopicCommentAdminDto> retrieve(Long id);
    ApiMessageDto<TopicCommentAdminDto> create(CreateTopicCommentForm createTopicCommentForm);
    ApiMessageDto<TopicCommentAdminDto> update(UpdateTopicCommentForm updateTopicCommentForm);
    ApiMessageDto<String> delete(Long id);
}
