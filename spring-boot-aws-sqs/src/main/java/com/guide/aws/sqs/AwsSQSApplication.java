package com.guide.aws.sqs;

import com.guide.aws.sqs.properties.AmazonSQSProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AwsSQSApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwsSQSApplication.class, args);
    }
}
