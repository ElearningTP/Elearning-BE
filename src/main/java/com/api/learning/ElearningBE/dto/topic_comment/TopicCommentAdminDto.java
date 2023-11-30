package com.api.learning.ElearningBE.dto.topic_comment;

import com.api.learning.ElearningBE.dto.BaseAdminDto;
import com.api.learning.ElearningBE.dto.account.AccountDto;
import com.api.learning.ElearningBE.dto.topic.TopicDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TopicCommentAdminDto extends BaseAdminDto {
    private String content;
    private TopicDto topicInfo;
    private AccountDto accountInfo;
}
