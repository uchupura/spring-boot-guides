package com.guide.event.service.member;

import com.guide.event.model.Member;
import com.guide.event.model.email.EmailTemplateType;
import com.guide.event.model.sms.SmsTemplateType;
import com.guide.event.repository.MemberRepository;
import com.guide.event.service.email.EmailService;
import com.guide.event.service.sms.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Profile("advanced")
@RequiredArgsConstructor
@Service
@Transactional
public class AdvancedMemberJoinService implements MemberJoinService {

    private final MemberRepository memberRepository;
    private final EmailService emailService;
    private final SmsService smsService;

    public Member join(Member member) {

        memberRepository.save(member);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                emailService.sendEmail(member.getEmail(), EmailTemplateType.JOIN);
                smsService.sendSms(member.getPhoneNo(), SmsTemplateType.JOIN);
            }
        });

        return member;
    }
}
