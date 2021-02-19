package com.guide.jpa.domain.customer3;

import com.guide.jpa.domain.customer2.Customer2;
import com.guide.jpa.global.Querydsl4RepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.guide.jpa.domain.customer3.QCustomer3.customer3;

@Repository
public class Customer3QueryRepository extends Querydsl4RepositorySupport {
    public Customer3QueryRepository() {
        super(Customer3.class);
    }

    public List<Customer3DTO> findAllCustomers() {
        return select(new QCustomer3DTO(customer3.id, customer3.name))
                .from(customer3)
                .fetch();
    }
}
