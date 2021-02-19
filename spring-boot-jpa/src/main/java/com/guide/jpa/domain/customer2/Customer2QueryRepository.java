package com.guide.jpa.domain.customer2;

import com.guide.jpa.global.Querydsl4RepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class Customer2QueryRepository extends Querydsl4RepositorySupport {
    public Customer2QueryRepository() {
        super(Customer2.class);
    }


}
