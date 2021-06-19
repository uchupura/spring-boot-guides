package com.guide.jpa;

import com.guide.jpa.global.FullBeanNameGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpringGuideApplication {

    public static void main(String[] args) {
        final SpringApplicationBuilder builder = new SpringApplicationBuilder(SpringGuideApplication.class);
        builder.beanNameGenerator(new FullBeanNameGenerator());
        builder.run(args);
    }
}
