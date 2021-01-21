package com.guide.aws.sqs.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "cloud.aws.sqs.queue")
public class AmazonSQSProperties {
    private String url;
}
