package com.guide.oauth2.resource.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private ExceptionType type;

    public BusinessException(ExceptionType type) {
        super(type.getMessage());
        this.type = type;
    }

    public BusinessException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
