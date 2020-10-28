package com.guide.event;

import com.guide.event.model.Member;
import com.guide.event.repository.MemberRepository;
import com.guide.event.service.member.MemberJoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@Slf4j
@EnableAsync(proxyTargetClass = true)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
public class SpringGuideApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringGuideApplication.class, args);
    }

    @Autowired
    private MemberJoinService memberJoinService;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("{} is injected", memberJoinService.getClass().getCanonicalName());
        try {
            Member member = Member.create("test", "test@test.com", "012-3456-7890");
            memberJoinService.join(member);
        } catch (Exception e) {
            log.error("{} was thrown", e.getClass().getCanonicalName());
        }
        log.info("member count : {}", memberRepository.count());
    }
}
