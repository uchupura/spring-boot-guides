package com.guide.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@Slf4j
@EnableAsync(proxyTargetClass = true)
@EnableAspectJAutoProxy
@SpringBootApplication
public class SpringGuideApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringGuideApplication.class, args);
    }
}
