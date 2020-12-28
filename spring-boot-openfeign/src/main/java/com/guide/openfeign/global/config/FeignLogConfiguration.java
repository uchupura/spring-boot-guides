package com.guide.openfeign.global.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class FeignLogConfiguration {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
