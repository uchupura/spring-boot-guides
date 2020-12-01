package com.guide.event.domain.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Customer {
    Long id;
    String name;
    Long number;
    int age;
    String phone;
    String address;
    String email;

    @Builder
    public Customer(Long id, String name, Long number, int age, String phone, String address, String email) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }
}
