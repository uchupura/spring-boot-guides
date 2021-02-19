package com.guide.event.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserEventListener {
    @TransactionalEventListener
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        log.info("Created User : " + event.getUser().getId());
    }

    @TransactionalEventListener
    public void handleUserPasswordChangedEvent(UserPasswordChangedEvent event) {
        log.info("Created User : " + event.getUser().getId());
    }
}
