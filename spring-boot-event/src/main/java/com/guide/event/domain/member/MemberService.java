package com.guide.event.domain.member;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
    private final MemberRepository repository;
    public MemberDTO.Response.Create createMember(MemberDTO.Request.Create body) {
        Member entity = MemberMapper.INSTANCE.map(body);
        Member member = repository.save(entity);
        return MemberMapper.INSTANCE.map(member);
    }

    public void deleteMember(Long id) {
        repository.deleteById(id);
    }

    public boolean hasMember(String name) {
        return repository.existsByName(name);
    }
}
