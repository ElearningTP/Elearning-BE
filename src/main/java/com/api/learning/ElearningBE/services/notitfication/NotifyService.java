package com.api.learning.ElearningBE.services.notitfication;

import com.api.learning.ElearningBE.constant.NotificationConstant;
import com.api.learning.ElearningBE.dto.BaseSendMsgForm;
import com.api.learning.ElearningBE.dto.notification.PostNotificationData;
import com.api.learning.ElearningBE.repositories.NotificationRepository;
import com.api.learning.ElearningBE.services.RabbitMQService;
import com.api.learning.ElearningBE.services.RabbitSender;
import com.api.learning.ElearningBE.storage.entities.Notification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class NotifyService {

    @Autowired
    private RabbitMQService rabbitMQService;

    @Value("${rabbitmq.app}")
    private String elearningAppName;

    @Value("${rabbitmq.notification.queue}")
    private String queueName;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private RabbitSender rabbitSender;
    @Autowired
    private NotificationRepository notificationRepository;

    public void sendMessage(String message, Integer kind, Long userId) {
        PostNotificationData data = new PostNotificationData();
        data.setMessage(message);
        data.setCmd(NotificationConstant.BACKEND_POST_NOTIFICATION_CMD);
        data.setKind(kind);
        data.setMessage(message);
        data.setUserId(userId);
        data.setApp(elearningAppName);
        handleSendMsg(data, NotificationConstant.BACKEND_POST_NOTIFICATION_CMD);
    }

    private <T> void handleSendMsg(T data, String cmd) {
        BaseSendMsgForm<T> form = new BaseSendMsgForm<>();
        form.setApp(NotificationConstant.BACKEND_APP);
        form.setCmd(cmd);
        form.setData(data);

        String msg;
        try {
            msg = objectMapper.writeValueAsString(form);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // create queue if existed
        rabbitMQService.createQueueIfNotExist(queueName);

        // push msg
        rabbitSender.send(msg, queueName);
    }

    public Notification createNotificationWithRefId(Boolean isRead, String refId){

        Notification notification = new Notification();
        notification.setIsRead(isRead);
        notification.setRefId(refId);
        notificationRepository.save(notification);
        return notification;
    }
}
