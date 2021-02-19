package com.guide.jpa.domain.customer;

import lombok.Getter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @ElementCollection
    private List<PhoneNumber> phoneNumbers = new ArrayList<>();

    public Customer(String name, PhoneNumber... phoneNumbers) {
        this.name = name;
        for (PhoneNumber phoneNumber : phoneNumbers) {
            this.phoneNumbers.add(phoneNumber);
        }
    }
}
