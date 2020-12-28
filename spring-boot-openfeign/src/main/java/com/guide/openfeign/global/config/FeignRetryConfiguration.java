package com.guide.openfeign.global.config;

import feign.Request;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import io.netty.handler.codec.http.HttpStatusClass;
import org.springframework.context.annotation.Bean;

import static java.lang.String.format;

public class FeignRetryConfiguration {
    @Bean
    public Retryer retryer() {
        return new Retryer.Default(3000, 3000, 3);
//        return new Retryer.Default();
    }

    @Bean
    public ErrorDecoder decoder() {
        return (methodKey, response) -> {
            if (HttpStatusClass.CLIENT_ERROR.contains(response.status()) || HttpStatusClass.SERVER_ERROR.contains(response.status())) {
                return new RetryableException(response.status(), format("%s 요청이 성공하지 못했습니다. Retry 합니다. - status: %s, headers: %s", methodKey, response.status(), response.headers()), response.request().httpMethod(), null, response.request());
            }
            return new IllegalStateException(format("%s 요청이 성공하지 못했습니다. - status: %s, headers: %s", methodKey, response.status(), response.headers()));
        };
    }
}
