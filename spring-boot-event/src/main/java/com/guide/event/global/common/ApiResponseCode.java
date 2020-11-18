package com.guide.event.global.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ApiResponseCode {
    OK(HttpStatus.OK, "C200", "OK"),
    CREATED(HttpStatus.CREATED, "C201", "Created"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "C400", "Bad Request"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "C401", "Unauthorized"),
    FORBIDDEN(HttpStatus.UNAUTHORIZED, "C403", "Forbidden"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "C404", "Not Found"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C405", "Method Not Allowed"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C500", "Internal Server Error"),



    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "C500", "Validation Error"),

    ORDER_QUANTITY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "O600", "Order Quantity Error")
    ;
    private final HttpStatus httpStatus;

    private final String code;

    private final String message;

    private final static Map<String, ApiResponseCode> map = new HashMap<>();

    static {
        for (ApiResponseCode type : ApiResponseCode.values()) {
            map.put(type.getCode(), type);
        }
    }

    ApiResponseCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public static ApiResponseCode fromCode(String code) {
        return map.get(code);
    }
}
