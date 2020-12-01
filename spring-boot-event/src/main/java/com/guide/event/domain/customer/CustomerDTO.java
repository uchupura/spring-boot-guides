package com.guide.event.domain.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerDTO {
    private Long id;
    private Record record;
    private Account account;

    @Builder
    public CustomerDTO(Long id, Record record, Account account) {
        this.id = id;
        this.record = record;
        this.account = account;
    }
}