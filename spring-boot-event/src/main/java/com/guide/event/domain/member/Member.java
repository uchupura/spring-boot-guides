package com.guide.event.domain.member;

import com.guide.event.domain.model.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String phone;
    private String email;
    @Embedded
    private Address address;

    @Builder
    public Member(String name, String password, String phone, String email, Address address) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }
}
