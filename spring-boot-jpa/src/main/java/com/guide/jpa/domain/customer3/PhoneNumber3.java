package com.guide.jpa.domain.customer3;

import com.guide.jpa.domain.customer.PhoneType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class PhoneNumber3 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private PhoneType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer3 customer3;

    public PhoneNumber3(String phoneNumber, PhoneType type, Customer3 customer3) {
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.customer3 = customer3;
    }
}
