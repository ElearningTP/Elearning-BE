package com.api.learning.ElearningBE.dto.topic;

import com.api.learning.ElearningBE.dto.account.AccountDto;
import com.api.learning.ElearningBE.dto.forum.ForumDto;
import lombok.Data;

@Data
public class TopicDto {
    private Long id;
    private String topicTitle;
    private String topicContent;
    private ForumDto forumInfo;
    private AccountDto accountInfo;
}
