package com.api.learning.ElearningBE.dto.topic;

import com.api.learning.ElearningBE.dto.account.AccountDto;
import com.api.learning.ElearningBE.dto.forum.ForumDto;
import com.api.learning.ElearningBE.dto.topic_comment.TopicCommentAdminDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TopicDto {
    private Long id;
    private String topicContent;
    private ForumDto forumInfo;
    private AccountDto accountInfo;
    private Date createDate;
    private Date modifiedDate;
    List<TopicCommentAdminDto> commentInfo;
}
