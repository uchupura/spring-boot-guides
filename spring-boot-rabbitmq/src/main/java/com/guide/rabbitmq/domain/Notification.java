package com.guide.rabbitmq.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Notification implements Serializable {

    private boolean hasError;
    private String message;
}
