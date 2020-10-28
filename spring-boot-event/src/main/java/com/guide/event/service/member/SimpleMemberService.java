package com.guide.event.service.member;

import com.guide.event.model.Member;
import com.guide.event.model.email.EmailTemplateType;
import com.guide.event.model.sms.SmsTemplateType;
import com.guide.event.repository.MemberRepository;
import com.guide.event.service.email.EmailService;
import com.guide.event.service.sms.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Profile("simple")
@Service
@Transactional
public class SimpleMemberService implements MemberJoinService {

    private final MemberRepository repository;
    private final EmailService emailService;
    private final SmsService smsService;

    @Override
    public Member join(Member member) {

        repository.save(member);

        emailService.sendEmail(member.getEmail(), EmailTemplateType.JOIN);
        smsService.sendSms(member.getPhoneNo(), SmsTemplateType.JOIN);

        return member;
    }
}
