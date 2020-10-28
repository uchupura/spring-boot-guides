package com.guide.event.service.member;

import com.guide.event.model.Member;
import com.guide.event.model.email.EmailTemplateType;
import com.guide.event.model.sms.SmsTemplateType;
import com.guide.event.service.email.EmailService;
import com.guide.event.service.sms.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class MemberJoinedEventListener {

    private final SmsService smsService;
    private final EmailService emailService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = EventMemberJoinService.MemberJoinedEvent.class)
    public void handle(EventMemberJoinService.MemberJoinedEvent event) {
        Member member = event.getMember();
        emailService.sendEmail(member.getEmail(), EmailTemplateType.JOIN);
        smsService.sendSms(member.getPhoneNo(), SmsTemplateType.JOIN);
    }
}
