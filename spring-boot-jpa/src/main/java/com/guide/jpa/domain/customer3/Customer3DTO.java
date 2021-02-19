package com.guide.jpa.domain.customer3;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.guide.jpa.domain.customer2.Customer2;
import com.guide.jpa.domain.customer2.PhoneNumber2DTO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Customer3DTO {
    private Long id;
    private String name;
    @Setter
    private List<PhoneNumber3DTO> phoneNumbers;

    public Customer3DTO(Customer3 customer3) {
        this.id = customer3.getId();
        this.name = customer3.getName();
        /*this.phoneNumbers = customer3.getPhoneNumbers().stream()
                .map(phoneNumber -> new PhoneNumber3DTO(phoneNumber))
                .collect(Collectors.toList());;*/
    }

    @QueryProjection
    public Customer3DTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
