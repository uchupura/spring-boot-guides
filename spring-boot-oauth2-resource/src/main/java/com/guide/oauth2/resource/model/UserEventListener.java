package com.guide.oauth2.resource.model;

import com.guide.oauth2.resource.model.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserEventListener {

    @Async
    @TransactionalEventListener
    public void handle1(UserCreatedEvent event) {
        log.info("Created User : " + event.getUser().getId());
    }

    @TransactionalEventListener
    public void handle2(UserPasswordChangedEvent event) {
        log.info("Created User : " + event.getUser().getId());
    }
}
