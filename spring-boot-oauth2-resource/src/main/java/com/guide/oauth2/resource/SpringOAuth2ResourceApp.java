package com.guide.oauth2.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync(proxyTargetClass = true)
@EnableAspectJAutoProxy
@SpringBootApplication
public class SpringOAuth2ResourceApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringOAuth2ResourceApp.class, args);
    }
}
