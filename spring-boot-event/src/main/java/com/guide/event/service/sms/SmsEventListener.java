package com.guide.event.service.sms;

import com.guide.event.model.SendableParameter;
import com.guide.event.service.member.AopAsyncEventMemberJoinService.AopAsyncMemberJoinedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Service
public class SmsEventListener {

    private final SmsService smsService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = AopAsyncMemberJoinedEvent.class)
    public void sendEmail(AopAsyncMemberJoinedEvent event) {
        SendableParameter request = event.getValue();
        smsService.sendSms(request.getPhoneNo(), request.getSmsTemplateType());
    }
}
