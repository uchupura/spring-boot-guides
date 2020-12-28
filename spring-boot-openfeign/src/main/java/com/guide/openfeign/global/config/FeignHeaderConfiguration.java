package com.guide.openfeign.global.config;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class FeignHeaderConfiguration {
    @Bean
    public RequestInterceptor requestInterceptor() {
        double dValue = Math.random();
        int iValue = (int)(dValue * 10);
        return requestTemplate -> requestTemplate.header("Authentication", String.valueOf(iValue));
    }

//    @Bean
//    Logger.Level feignLoggerLevel() {
//        return Logger.Level.FULL;
//    }
}
