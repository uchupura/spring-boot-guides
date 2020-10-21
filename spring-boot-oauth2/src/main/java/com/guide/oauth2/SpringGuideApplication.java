package com.guide.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SpringGuideApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringGuideApplication.class, args);
    }
}
