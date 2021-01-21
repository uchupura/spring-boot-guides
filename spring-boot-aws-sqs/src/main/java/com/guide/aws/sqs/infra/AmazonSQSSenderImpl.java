package com.guide.aws.sqs.infra;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guide.aws.sqs.dto.Message;
import com.guide.aws.sqs.properties.AmazonSQSProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class AmazonSQSSenderImpl implements AmazonSQSSender{
    private final ObjectMapper objectMapper;
    private final AmazonSQS amazonSQS;
    private final AmazonSQSProperties properties;

    @Override
    public SendMessageResult sendMessage(Message msg) throws JsonProcessingException {
        SendMessageRequest sendMessageRequest = new SendMessageRequest(properties.getUrl(), objectMapper.writeValueAsString(msg))
                .withMessageGroupId("live-commerce")
                .withMessageDeduplicationId(UUID.randomUUID().toString());
        return amazonSQS.sendMessage(sendMessageRequest);
    }
}
