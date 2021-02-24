package com.guide.rabbitmq2.model;

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
