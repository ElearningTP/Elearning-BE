package com.api.learning.ElearningBE.dto.notification;

import com.api.learning.ElearningBE.dto.BaseDataForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PostNotificationData extends BaseDataForm {
    private Integer kind;
    private String message;
    private Long userId;
    private String cmd;
}