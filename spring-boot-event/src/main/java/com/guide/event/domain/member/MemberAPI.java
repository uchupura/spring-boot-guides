package com.guide.event.domain.member;

import com.guide.event.global.common.CommonApiResponse;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.guide.event.global.common.CommonApiResponse.success;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(MemberAPI.URL)
public class MemberAPI {
    public static final String URL = "/members";

    private final MemberService service;

    @Validated(MemberDTO.OnCreate.class)
    @PostMapping
    public CommonApiResponse createMember(@RequestBody @Valid MemberDTO.Request.Create body) {
        return success(service.createMember(body));
    }

    @DeleteMapping("{id}")
    public CommonApiResponse deleteMember(@PathVariable("id") Long id) {
        service.deleteMember(id);
        return success();
    }
}
