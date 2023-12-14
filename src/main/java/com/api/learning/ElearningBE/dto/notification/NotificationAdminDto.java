package com.api.learning.ElearningBE.dto.notification;

import com.api.learning.ElearningBE.dto.BaseAdminDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotificationAdminDto extends BaseAdminDto {
    private String message;
    private Boolean isRead;
    private Long refId;
    private Long userId;
}
