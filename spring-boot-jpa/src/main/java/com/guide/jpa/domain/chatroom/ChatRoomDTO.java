package com.guide.jpa.domain.chatroom;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ChatRoomDTO {
    public static class Request {
        @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "messageType")
        @JsonSubTypes({
                @JsonSubTypes.Type(value=SmsChatRoom.class, name = "SmsChatRoom"),
                @JsonSubTypes.Type(value=TAgentChatRoom.class, name = "TAgentChatRoom")
        })
        @Getter
        @NoArgsConstructor
        public abstract static class ChatRoom {
            private Long categoryId;
            private Long counselorId;

            public ChatRoom(Long categoryId, Long counselorId) {
                this.categoryId = categoryId;
                this.counselorId = counselorId;
            }
        }

        @Getter
        @NoArgsConstructor
        public static class SmsChatRoom extends ChatRoom {
            private Long domainId;

            @Builder
            public SmsChatRoom(Long categoryId, Long counselorId, Long domainId) {
                super(categoryId ,counselorId);
                this.domainId = domainId;
            }
        }

        @Getter
        @NoArgsConstructor
        public static class TAgentChatRoom extends ChatRoom {
            private String name;

            @Builder
            public TAgentChatRoom(Long categoryId, Long counselorId, String name) {
                super(categoryId ,counselorId);
                this.name = name;
            }
        }
    }
}