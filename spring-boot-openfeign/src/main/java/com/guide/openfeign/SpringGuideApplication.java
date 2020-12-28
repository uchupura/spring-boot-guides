package com.guide.openfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SpringGuideApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringGuideApplication.class, args);
    }
}
