package com.guide.jpa.domain.customer2;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CardDTO {
    private Long id;
    private String cardName;
    private String customerName;

    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardName = card.getCardName();
        this.customerName = card.getCustomer().getName();
    }
}
