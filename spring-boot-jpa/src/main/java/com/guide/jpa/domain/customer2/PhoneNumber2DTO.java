package com.guide.jpa.domain.customer2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.guide.jpa.domain.customer.PhoneType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PhoneNumber2DTO {
    private Long id;
    private String phoneNumber;
    private PhoneType type;
    private String customerName;

    public PhoneNumber2DTO(PhoneNumber2 phoneNumber2) {
        this.id = phoneNumber2.getId();
        this.phoneNumber = phoneNumber2.getPhoneNumber();
        this.type = phoneNumber2.getType();
        this.customerName = phoneNumber2.getCustomer2().getName();
    }
}
