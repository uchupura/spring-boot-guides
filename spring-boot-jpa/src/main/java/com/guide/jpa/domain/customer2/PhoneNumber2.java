package com.guide.jpa.domain.customer2;

import com.guide.jpa.domain.customer.PhoneType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class PhoneNumber2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private PhoneType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer2 customer2;

    public PhoneNumber2(String phoneNumber, PhoneType type, Customer2 customer2) {
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.customer2 = customer2;
    }
}
