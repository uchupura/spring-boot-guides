package com.guide.event.domain.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Account {
    String name;
    int age;
    String phone;
    String address;
    String email;

    @Builder
    public Account(String name, int age, String phone, String address, String email) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }
}