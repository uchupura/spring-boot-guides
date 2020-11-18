package com.guide.event.global.error;

import com.guide.event.global.common.ApiResponseCode;
import com.guide.event.global.common.CommonApiResponse;
import com.guide.event.global.error.exception.BusinessException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.guide.event.global.common.ApiResponseCode.*;
import static com.guide.event.global.common.CommonApiResponse.error;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     *  javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     *  HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     *  주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected HttpEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(">> handleMethodArgumentNotValidException", e);
        return error(VALIDATION_ERROR, e.getBindingResult());
    }

    /**
     * @ModelAttribut 으로 binding error 발생시 BindException 발생한다.
     * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
     */
    @ExceptionHandler(BindException.class)
    protected HttpEntity<Object> handleBindException(BindException e) {
        log.error(">> handleBindException", e);
        return error(VALIDATION_ERROR, e.getBindingResult());
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected HttpEntity<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(">> handleHttpRequestMethodNotSupportedException", e);
        return error(METHOD_NOT_ALLOWED);
    }

    /**
     * Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생합
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected HttpEntity<Object> handleAccessDeniedException(AccessDeniedException e) {
        log.error(">> handleAccessDeniedException", e);
        return error(UNAUTHORIZED);
    }

    @ExceptionHandler(BusinessException.class)
    protected HttpEntity<Object> handleBusinessException(final BusinessException e) {
        log.error(">> handleBusinessException", e);
        return error(e.getExceptionCode(), e.getMessage(), FieldError.of(e.getStackTrace()));
    }

    @ExceptionHandler(Exception.class)
    protected HttpEntity<Object> handleException(Exception e) {
        log.error(">> handleEntityNotFoundException", e);
        return error(INTERNAL_SERVER_ERROR, e.toString(), FieldError.of(e.getStackTrace()));
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String className;
        private String methodName;
        private String fileName;
        private int lineNumber;

        private FieldError(final String className, final String methodName, final String fileName, final int lineNumber) {
            this.className = className;
            this.methodName = methodName;
            this.fileName = fileName;
            this.lineNumber = lineNumber;
        }

        public static List<FieldError> of(final String className, final String methodName, final String fileName, final int lineNumber) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(className, methodName, fileName, lineNumber));
            return fieldErrors;
        }

        private static List<FieldError> of(final StackTraceElement[] stackTraceElements) {
            return Arrays.stream(stackTraceElements)
                    .filter(s -> {
                        return s.getClassName().startsWith("com.guide") && !s.getMethodName().contains("lambda") && s.getLineNumber() != -1;
                    })
                    .map(error -> new FieldError(
                            error.getClassName(),
                            error.getMethodName(),
                            error.getFileName(),
                            error.getLineNumber()))
                    .collect(Collectors.toList());
        }
    }
}
