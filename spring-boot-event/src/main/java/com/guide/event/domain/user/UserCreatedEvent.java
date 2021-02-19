package com.guide.event.domain.user;

import lombok.Getter;

@Getter
public class UserCreatedEvent {
    private User user;

    public UserCreatedEvent(User user) {
        this.user = user;
    }
}
