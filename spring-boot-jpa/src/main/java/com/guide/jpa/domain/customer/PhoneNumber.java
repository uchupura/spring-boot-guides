package com.guide.jpa.domain.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@NoArgsConstructor
@Embeddable
public class PhoneNumber {
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private PhoneType type;

    public PhoneNumber(String phoneNumber, PhoneType type) {
        this.phoneNumber = phoneNumber;
        this.type = type;
    }
}
