package com.guide.common.exception;

import lombok.Getter;
import com.guide.common.enumtype.ExceptionType;

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
