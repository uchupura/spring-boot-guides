package com.guide.event.service.member;

import com.guide.event.model.Member;
import com.guide.event.repository.MemberRepository;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Profile("event")
@RequiredArgsConstructor
@Service
@Transactional
public class EventMemberJoinService implements ApplicationEventPublisherAware, MemberJoinService {

    private final MemberRepository memberRepository;

    private ApplicationEventPublisher eventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @Override
    public Member join(Member member) {

        memberRepository.save(member);
        eventPublisher.publishEvent(new MemberJoinedEvent(member));

        return member;
    }

    public static class MemberJoinedEvent {

        @Getter
        private Member member;

        private MemberJoinedEvent(@NonNull Member member) {
            this.member = member;
        }
    }
}
