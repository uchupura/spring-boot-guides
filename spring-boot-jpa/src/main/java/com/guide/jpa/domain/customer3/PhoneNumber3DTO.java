package com.guide.jpa.domain.customer3;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.guide.jpa.domain.customer.PhoneType;
import com.guide.jpa.domain.customer2.PhoneNumber2;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PhoneNumber3DTO {
    private Long id;
    private String phoneNumber;
    private PhoneType type;
    private String customerName;

    public PhoneNumber3DTO(PhoneNumber3 phoneNumber3) {
        this.id = phoneNumber3.getId();
        this.phoneNumber = phoneNumber3.getPhoneNumber();
        this.type = phoneNumber3.getType();
        this.customerName = phoneNumber3.getCustomer3().getName();
    }
}
