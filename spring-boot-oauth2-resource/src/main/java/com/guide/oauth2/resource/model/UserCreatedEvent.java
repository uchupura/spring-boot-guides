package com.guide.oauth2.resource.model;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserCreatedEvent extends ApplicationEvent {
    private User user;
    public UserCreatedEvent(User user) {
        super(user);
        this.user = user;
    }
}
