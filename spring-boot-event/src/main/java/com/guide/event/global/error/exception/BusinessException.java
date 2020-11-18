package com.guide.event.global.error.exception;

import com.guide.event.global.common.ApiResponseCode;

public class BusinessException extends RuntimeException {

    private ApiResponseCode code;

    public BusinessException(ApiResponseCode code) {
        super(code.getMessage());
        this.code = code;
    }
    public BusinessException(String message, ApiResponseCode code) {
        super(message);
        this.code = code;
    }

    public ApiResponseCode getExceptionCode() {
        return code;
    }
}
