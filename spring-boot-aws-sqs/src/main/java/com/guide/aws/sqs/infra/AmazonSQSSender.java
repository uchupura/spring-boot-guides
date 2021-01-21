package com.guide.aws.sqs.infra;

import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.guide.aws.sqs.dto.Message;

public interface AmazonSQSSender {
    SendMessageResult sendMessage(Message message) throws JsonProcessingException;
}
