package com.api.learning.ElearningBE.dto.topic_comment;

import com.api.learning.ElearningBE.dto.account.AccountDto;
import com.api.learning.ElearningBE.dto.topic.TopicDto;
import lombok.Data;

@Data
public class TopicCommentDto {
    private Long id;
    private String content;
    private TopicDto topicInfo;
    private AccountDto accountDto;
}
