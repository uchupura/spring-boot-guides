package com.guide.batch.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class MemberInfo {

    private Long memberId;
    private String name;
    private String address;
    private String role;
}
