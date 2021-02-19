package com.guide.oauth2.resource.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.guide.oauth2.resource.exception.ExceptionType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CommonApiResponse {
    private static final String OK_CODE = "SS001";
    private static final String OK_MESSAGE = "OK";

    private String code;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
    private Object data;

    @Builder
    public CommonApiResponse(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public static CommonApiResponse success() {
        CommonApiResponse response = CommonApiResponse.builder()
                .code(OK_CODE)
                .message(OK_MESSAGE)
                .build();
        return response;
    }

    public static CommonApiResponse success(Object object) {
        CommonApiResponse response = CommonApiResponse.builder()
                .code(OK_CODE)
                .message(OK_MESSAGE)
                .data(object)
                .build();
        return response;
    }

    public static ResponseEntity<Object> error(ExceptionType type) {
        CommonApiResponse response = CommonApiResponse.builder()
                .code(type.getCode())
                .message(type.getMessage())
                .build();
        return new ResponseEntity<Object>(response, HttpStatus.valueOf(type.getStatus()));
    }
    public static ResponseEntity<Object> error(ExceptionType type, String message) {
        CommonApiResponse response = CommonApiResponse.builder()
                .code(type.getCode())
                .message(message)
                .build();
        return new ResponseEntity<Object>(response, HttpStatus.valueOf(type.getStatus()));
    }
    public static ResponseEntity<Object> error(ExceptionType type, String message, Object data) {
        CommonApiResponse response = CommonApiResponse.builder()
                .code(type.getCode())
                .message(message)
                .data(data)
                .build();
        return new ResponseEntity<Object>(response, HttpStatus.valueOf(type.getStatus()));
    }
    public static ResponseEntity<Object> error(ExceptionType type, BindingResult bindingResult) {
        CommonApiResponse response = CommonApiResponse.builder()
                .code(type.getCode())
                .message(type.getMessage())
                .data(FieldError.of(bindingResult))
                .build();
        return new ResponseEntity<Object>(response, HttpStatus.valueOf(type.getStatus()));
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}
