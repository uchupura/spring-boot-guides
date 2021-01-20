package com.guide.jpa.domain.common.model;

import lombok.Getter;

@Getter
public class Money {
    int value;

    public Money(int value) {
        this.value = value;
    }

    public Money multiply(int multiplier) {
        return new Money(value * multiplier);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}

