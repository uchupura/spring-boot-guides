package com.guide.jpa.domain.customer;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@Rollback(value = false)
@Transactional
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerTest {
    private Long customerId = null;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Order(1)
    public void 고객등록() {
        Customer customer = new Customer("홍길동", new PhoneNumber("0317091111", PhoneType.House), new PhoneNumber("01077777777", PhoneType.Cell));
        Customer savedCustomer = customerRepository.save(customer);

        Customer customer1 = new Customer("홍길동1", new PhoneNumber("0317091111", PhoneType.House), new PhoneNumber("01077777777", PhoneType.Cell));
        Customer savedCustomer1 = customerRepository.save(customer1);

        customerId = savedCustomer.getId();
    }

    @Test
    @Order(2)
    public void 고객검색() {
        customerRepository.findById(customerId).ifPresent(e -> {
            System.out.println("ID : " + e.getId());
            System.out.println("Name : " + e.getName());
            for (PhoneNumber phoneNumber : e.getPhoneNumbers()) {
                System.out.println(phoneNumber.getPhoneNumber());
            }
        });
    }

    @Test
    @Order(3)
    public void 고객리스트검색() {
        List<Customer> customers = customerRepository.findAll();
        System.out.println(customers.toString());
    }
    /*@Test
    @Order(3)
    public void 고객삭제() {
        customerRepository.deleteById(customerId);
    }*/
}