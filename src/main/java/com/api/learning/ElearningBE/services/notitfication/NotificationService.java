package com.api.learning.ElearningBE.services.notitfication;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.notification.NotificationAdminDto;
import com.api.learning.ElearningBE.dto.notification.NotificationDto;
import com.api.learning.ElearningBE.form.notification.CreateNotificationForm;
import com.api.learning.ElearningBE.form.notification.UpdateNotificationForm;
import com.api.learning.ElearningBE.storage.criteria.NotificationCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {
    ApiMessageDto<ResponseListDto<List<NotificationDto>>> list(NotificationCriteria notificationCriteria, Pageable pageable);
    ApiMessageDto<NotificationAdminDto> retrieve(Long id);
    ApiMessageDto<String> readAllNotification();
//    ApiMessageDto<NotificationDto> create(CreateNotificationForm createNotificationForm);
//    ApiMessageDto<NotificationDto> update(UpdateNotificationForm updateNotificationForm);
    ApiMessageDto<String> delete(Long id);
}
