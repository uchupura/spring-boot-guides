package com.guide.event.domain.customer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Setter;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Record {
    String name;
    Long number;

    @Builder
    public Record(String name, Long number) {
        this.name = name;
        this.number = number;
    }
}