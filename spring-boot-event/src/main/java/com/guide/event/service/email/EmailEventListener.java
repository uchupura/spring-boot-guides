package com.guide.event.service.email;

import com.guide.event.model.SendableParameter;
import com.guide.event.service.member.AopAsyncEventMemberJoinService.AopAsyncMemberJoinedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Service
public class EmailEventListener {

    private final EmailService emailService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = AopAsyncMemberJoinedEvent.class)
    public void handleMemberJoinedEvent(AopAsyncMemberJoinedEvent event) {
        SendableParameter parameter = event.getValue();
        emailService.sendEmail(parameter.getEmail(), parameter.getEmailTemplateType());
    }
}
