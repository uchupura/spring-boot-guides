package com.guide.jpa.domain.customer2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Customer2DTO {
    private Long id;
    private String name;
    private List<PhoneNumber2DTO> phoneNumbers;
    private List<CardDTO> cards;

    public Customer2DTO(Customer2 customer2) {
        this.id = customer2.getId();
        this.name = customer2.getName();
        this.phoneNumbers = customer2.getPhoneNumbers().stream()
                .map(phoneNumber -> new PhoneNumber2DTO(phoneNumber))
                .collect(Collectors.toList());;
        this.cards = customer2.getCards().stream()
                .map(card -> new CardDTO(card))
                .collect(Collectors.toList());
    }
}
