package com.api.learning.ElearningBE.dto;

import lombok.Data;

@Data
public class BaseSendMsgForm<T> {
    private String cmd;
    private String app;
    private T data;
}
