package com.guide.event.global.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
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
                .code(ApiResponseCode.OK.getCode())
                .message(ApiResponseCode.OK.getMessage())
                .build();
        return response;
    }

    public static CommonApiResponse success(Object object) {
        CommonApiResponse response = CommonApiResponse.builder()
                .code(ApiResponseCode.OK.getCode())
                .message(ApiResponseCode.OK.getMessage())
                .data(object)
                .build();
        return response;
    }

    public static ResponseEntity<Object> error(ApiResponseCode code) {
        CommonApiResponse response = CommonApiResponse.builder()
                .code(code.getCode())
                .message(code.getMessage())
                .build();
        return new ResponseEntity<Object>(response, code.getHttpStatus());
    }
    public static ResponseEntity<Object> error(ApiResponseCode code, String message) {
        CommonApiResponse response = CommonApiResponse.builder()
                .code(code.getCode())
                .message(message)
                .build();
        return new ResponseEntity<Object>(response, code.getHttpStatus());
    }
    public static ResponseEntity<Object> error(ApiResponseCode code, String message, Object data) {
        CommonApiResponse response = CommonApiResponse.builder()
                .code(code.getCode())
                .message(message)
                .data(data)
                .build();
        return new ResponseEntity<Object>(response, code.getHttpStatus());
    }
    public static ResponseEntity<Object> error(ApiResponseCode code, BindingResult bindingResult) {
        CommonApiResponse response = CommonApiResponse.builder()
                .code(code.getCode())
                .message(code.getMessage())
                .data(FieldError.of(bindingResult))
                .build();
        return new ResponseEntity<Object>(response, code.getHttpStatus());
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
