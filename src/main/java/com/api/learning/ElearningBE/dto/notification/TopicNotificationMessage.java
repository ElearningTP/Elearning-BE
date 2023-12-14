package com.api.learning.ElearningBE.dto.notification;

import lombok.Data;

@Data
public class TopicNotificationMessage {
    private Long notificationId;
    private Long forumId;
    private Long topicId;
    private String topicContent;
    private String forumTitle;
    private String kind;
}
