package com.guide.event.domain.user;

import lombok.Getter;

@Getter
public class UserPasswordChangedEvent {
    private User user;

    public UserPasswordChangedEvent(User user) {
        this.user = user;
    }
}
