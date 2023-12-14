package com.api.learning.ElearningBE.mapper;

import com.api.learning.ElearningBE.dto.notification.NotificationAdminDto;
import com.api.learning.ElearningBE.dto.notification.NotificationDto;
import com.api.learning.ElearningBE.form.notification.CreateNotificationForm;
import com.api.learning.ElearningBE.form.notification.UpdateNotificationForm;
import com.api.learning.ElearningBE.storage.entities.Notification;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NotificationMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "message", target = "message")
    Notification fromCreateNotificationFormToEntity(CreateNotificationForm createNotificationForm);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "message", target = "message")
    void fromUpdateNotificationToEntity(UpdateNotificationForm updateNotificationForm, @MappingTarget Notification notification);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "message", target = "message")
    @Mapping(source = "isRead", target = "isRead")
    @Mapping(source = "refId", target = "refId")
    @Mapping(source = "idUser", target = "userId")
    @Named("fromEntityToNotificationDto")
    NotificationDto fromEntityToNotificationDto(Notification notification);
    @IterableMapping(elementTargetType = NotificationDto.class, qualifiedByName = "fromEntityToNotificationDto")
    List<NotificationDto> fromEntityToNotificationDtoList(List<Notification> notifications);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id")
    @Mapping(source = "message", target = "message")
    @Mapping(source = "isRead", target = "isRead")
    @Mapping(source = "refId", target = "refId")
    @Mapping(source = "idUser", target = "userId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    NotificationAdminDto fromEntityToNotificationAdminDto(Notification notification);
}
