package com.api.learning.ElearningBE.dto.notification;

import lombok.Data;

@Data
public class NotificationDto {
    private Long id;
    private String message;
    private Boolean isRead;
    private Long refId;
    private Long userId;
    private String kind;
}
