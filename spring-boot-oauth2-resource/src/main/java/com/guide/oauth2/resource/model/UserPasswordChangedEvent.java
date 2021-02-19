package com.guide.oauth2.resource.model;

import lombok.Getter;

@Getter
public class UserPasswordChangedEvent {
    private User user;

    public UserPasswordChangedEvent(User user) {
        this.user = user;
    }
}
