package com.guide.aws.sqs.dto;

import lombok.Getter;

@Getter
public class Message {
    private String type;
    private String msg;

    public Message(String type, String message) {
        this.type = type;
        this.msg = message;
    }
}
