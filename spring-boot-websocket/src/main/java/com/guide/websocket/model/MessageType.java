package com.guide.websocket.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public enum MessageType {
    @JsonProperty("CHAT")CHAT,
    @JsonProperty("JOIN")JOIN,
    @JsonProperty("LEAVE")LEAVE;
}
