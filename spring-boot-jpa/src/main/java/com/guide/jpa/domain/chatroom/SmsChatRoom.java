package com.guide.jpa.domain.chatroom;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue("SMS")
public class SmsChatRoom extends ChatRoom {
    private Long domainId;

    public SmsChatRoom(Long categoryId, Long counselorId, Long domainId) {
        super(categoryId, counselorId);
        this.domainId = domainId;
    }
}
