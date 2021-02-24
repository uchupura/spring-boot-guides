package com.guide.jpa.domain.chatroom;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue("T")
public class TAgentChatRoom extends ChatRoom {
    private String name;

    public TAgentChatRoom(Long categoryId, Long counselorId, String name) {
        super(categoryId, counselorId);
        this.name = name;
    }
}
