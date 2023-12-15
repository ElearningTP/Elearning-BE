package com.api.learning.ElearningBE.services.notitfication;

import com.api.learning.ElearningBE.dto.ApiMessageDto;
import com.api.learning.ElearningBE.dto.ResponseListDto;
import com.api.learning.ElearningBE.dto.notification.NotificationAdminDto;
import com.api.learning.ElearningBE.dto.notification.NotificationDto;
import com.api.learning.ElearningBE.exceptions.NotFoundException;
import com.api.learning.ElearningBE.mapper.NotificationMapper;
import com.api.learning.ElearningBE.repositories.NotificationRepository;
import com.api.learning.ElearningBE.storage.criteria.NotificationCriteria;
import com.api.learning.ElearningBE.storage.entities.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public ApiMessageDto<ResponseListDto<List<NotificationDto>>> list(NotificationCriteria notificationCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<List<NotificationDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<NotificationDto>> responseListDto = new ResponseListDto<>();
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createDate").descending());
        Page<Notification> notifications = notificationRepository.findAll(notificationCriteria.getSpecification(),pageable);
        List<NotificationDto> notificationDtoList = notificationMapper.fromEntityToNotificationDtoList(notifications.getContent());

        responseListDto.setPageIndex(notifications.getNumber());
        responseListDto.setContent(notificationDtoList);
        responseListDto.setPageSize(notifications.getSize());
        responseListDto.setTotalPages(notifications.getTotalPages());
        responseListDto.setTotalElements(notifications.getTotalElements());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Retrieve notification list successfully");
        return apiMessageDto;
    }

    @Override
    public ApiMessageDto<NotificationAdminDto> retrieve(Long id) {
        ApiMessageDto<NotificationAdminDto> apiMessageDto = new ApiMessageDto<>();
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Notification with id %s not found", id)));
        NotificationAdminDto notificationAdminDto = notificationMapper.fromEntityToNotificationAdminDto(notification);

        apiMessageDto.setData(notificationAdminDto);
        apiMessageDto.setMessage("Retrieve notification successfully");
        return apiMessageDto;
    }

//    @Override
//    public ApiMessageDto<NotificationDto> create(CreateNotificationForm createNotificationForm) {
//        ApiMessageDto<NotificationDto> apiMessageDto = new ApiMessageDto<>();
//        Account account = accountRepository.findById(createNotificationForm.getAccountId())
//                .orElseThrow(() -> new NotFoundException(String.format("Account with id %s not found", createNotificationForm.getAccountId())));
//        Notification notification = notificationMapper.fromCreateNotificationFormToEntity(createNotificationForm);
////        notification.setAccount(account);
//        notificationRepository.save(notification);
//        NotificationDto notificationDto = notificationMapper.fromEntityToNotificationDto(notification);
//
//        apiMessageDto.setData(notificationDto);
//        apiMessageDto.setMessage("Create notification successfully");
//        return apiMessageDto;
//    }
//
//    @Override
//    public ApiMessageDto<NotificationDto> update(UpdateNotificationForm updateNotificationForm) {
//        ApiMessageDto<NotificationDto> apiMessageDto = new ApiMessageDto<>();
//        Notification notification = notificationRepository.findById(updateNotificationForm.getId())
//                .orElseThrow(() -> new NotFoundException(String.format("Notification with id %s not found", updateNotificationForm.getId())));
//        notificationMapper.fromUpdateNotificationToEntity(updateNotificationForm, notification);
//        notificationRepository.save(notification);
//        NotificationDto notificationDto = notificationMapper.fromEntityToNotificationDto(notification);
//
//        apiMessageDto.setData(notificationDto);
//        apiMessageDto.setMessage("Update notification successfully");
//        return apiMessageDto;
//    }

    @Override
    public ApiMessageDto<String> delete(Long id) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Notification with id %s not found", id)));
        notificationRepository.delete(notification);

        apiMessageDto.setMessage("Delete notification successfully");
        return apiMessageDto;
    }
}
