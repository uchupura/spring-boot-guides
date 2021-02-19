package com.guide.jpa.domain.customer2;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Customer2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "customer2")
    private List<PhoneNumber2> phoneNumbers = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    private List<Card> cards = new ArrayList<>();

    public Customer2(String name) {
        this.name = name;
    }
}
