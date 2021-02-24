package com.guide.jpa.domain.chatroom;

import lombok.*;

import javax.persistence.*;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "chat_type")
public abstract class ChatRoom {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long categoryId;
    private Long counselorId;

    public ChatRoom(Long categoryId, Long counselorId) {
        this.categoryId = categoryId;
        this.counselorId = counselorId;
    }
}
