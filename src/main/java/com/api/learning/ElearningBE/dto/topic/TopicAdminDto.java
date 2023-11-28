package com.api.learning.ElearningBE.dto.topic;

import com.api.learning.ElearningBE.dto.BaseAdminDto;
import com.api.learning.ElearningBE.dto.account.AccountDto;
import com.api.learning.ElearningBE.dto.forum.ForumDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TopicAdminDto extends BaseAdminDto {
    private String topicTitle;
    private String topicContent;
    private ForumDto forumInfo;
    private AccountDto accountInfo;
}
