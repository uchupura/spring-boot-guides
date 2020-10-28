package com.guide.event.service.member;

import com.guide.event.aop.PublishEvent;
import com.guide.event.model.Member;
import com.guide.event.model.SendableParameter;
import com.guide.event.model.event.EventHoldingValue;
import com.guide.event.repository.MemberRepository;
import com.guide.event.aop.EventPublisher;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Profile("aop-async-event")
@RequiredArgsConstructor
@Service
@Transactional
public class AopAsyncEventMemberJoinService extends EventPublisher implements MemberJoinService {

    private final MemberRepository memberRepository;

    @PublishEvent(eventType = AopAsyncMemberJoinedEvent.class, params="#{T(com.guide.event.model.SendableParameter).create(name, email, phoneNo)}")
    public Member join(Member member) {

        memberRepository.save(member);
        // publishEvent(new AopAsyncMemberJoinedEvent(SendableParameter.create(member.getName(), member.getEmail(), member.getPhoneNo())));
        return member;
    }

    public static class AopAsyncMemberJoinedEvent implements EventHoldingValue<SendableParameter> {

        @Getter
        private SendableParameter value;

        public AopAsyncMemberJoinedEvent(@NonNull SendableParameter param) {
            this.value = param;
        }
    }
}
