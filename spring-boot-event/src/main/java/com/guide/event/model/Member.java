package com.guide.event.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phoneNo;

    @Builder
    private Member(String name, String email, String phoneNo) {
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public static Member create(@NonNull String name, @NonNull String email, @NonNull String phoneNo) {

        return Member.builder()
                .name(name)
                .email(email)
                .phoneNo(phoneNo)
                .build();
    }
}