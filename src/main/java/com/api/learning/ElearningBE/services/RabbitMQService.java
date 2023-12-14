package com.api.learning.ElearningBE.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class RabbitMQService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private RabbitSender rabbitSender;

    public void createQueueIfNotExist(String queueName){
        rabbitSender.createQueueIfNotExist(queueName);
    }
}
