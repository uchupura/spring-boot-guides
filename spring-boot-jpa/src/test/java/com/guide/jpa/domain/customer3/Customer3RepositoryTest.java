package com.guide.jpa.domain.customer3;

import com.guide.jpa.domain.customer.PhoneType;
import com.guide.jpa.domain.customer2.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@Rollback(value = false)
@Transactional
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Customer3RepositoryTest {
    private Long customerId = null;
    @Autowired
    private EntityManager em;

    @Autowired
    private Customer3Repository customer3Repository;

    @Autowired
    private PhoneNumber3Repository phoneNumber3Repository;

    @Autowired
    private Customer3QueryRepository customer3QueryRepository;

    @Test
    @Order(1)
    public void 고객등록() {
        Customer3 customer = new Customer3("홍길동");
        Customer3 savedCustomer = customer3Repository.save(customer);

        PhoneNumber3 phoneNumber1 = new PhoneNumber3("0317091111", PhoneType.House, customer);
        phoneNumber3Repository.save(phoneNumber1);

        PhoneNumber3 phoneNumber2 = new PhoneNumber3("01077777777", PhoneType.Cell, customer);
        phoneNumber3Repository.save(phoneNumber2);


        Customer3 customer2 = new Customer3("홍길동2");
        Customer3 savedCustomer2 = customer3Repository.save(customer2);

        PhoneNumber3 phoneNumber2_1 = new PhoneNumber3("0317091111", PhoneType.House, customer2);
        phoneNumber3Repository.save(phoneNumber2_1);

        PhoneNumber3 phoneNumber2_2 = new PhoneNumber3("01077777777", PhoneType.Cell, customer2);
        phoneNumber3Repository.save(phoneNumber2_2);



        Customer3 customer3 = new Customer3("홍길동3");
        Customer3 savedCustomer3 = customer3Repository.save(customer3);

        PhoneNumber3 phoneNumber3_1 = new PhoneNumber3("0317091111", PhoneType.House, customer3);
        phoneNumber3Repository.save(phoneNumber3_1);

        PhoneNumber3 phoneNumber3_2 = new PhoneNumber3("01077777777", PhoneType.Cell, customer3);
        phoneNumber3Repository.save(phoneNumber3_2);



        Customer3 customer4 = new Customer3("홍길동4");
        Customer3 savedCustomer4 = customer3Repository.save(customer4);

        PhoneNumber3 phoneNumber4_1 = new PhoneNumber3("0317091111", PhoneType.House, customer4);
        phoneNumber3Repository.save(phoneNumber4_1);

        PhoneNumber3 phoneNumber4_2 = new PhoneNumber3("01077777777", PhoneType.Cell, customer4);
        phoneNumber3Repository.save(phoneNumber4_2);

        customerId = savedCustomer.getId();
    }

    @Test
    @Order(2)
    public void 고객검색() {
        Customer3 customer = customer3Repository.findById(customerId).orElseThrow(() -> new RuntimeException());
        Customer3DTO customer2DTO = new Customer3DTO(customer);
    }

    @Test
    @Order(3)
    public void 고객리스트검색() {
        List<Customer3> customers = customer3Repository.findAll();
        List<Customer3DTO> customerDTOS = customers.stream()
                .map(customer -> new Customer3DTO(customer))
                .collect(Collectors.toList());
        Map<Long, List<PhoneNumber3DTO>> phoneNumbers = findPhoneNumbers(customerIds(customerDTOS));
        customerDTOS.forEach(o -> o.setPhoneNumbers(phoneNumbers.get(o.getId())));

        System.out.println(customerDTOS);
    }
    private List<Long> customerIds(List<Customer3DTO> customers) {
        return customers.stream()
                .map(o -> o.getId())
                .collect(Collectors.toList());
    }
    private Map<Long, List<PhoneNumber3DTO>> findPhoneNumbers(List<Long> customerIds) {
        List<PhoneNumber3> phoneNumbers = phoneNumber3Repository.findAllById(customerIds);
        List<PhoneNumber3DTO> phoneNumberDTOS = phoneNumbers.stream()
                .map(o -> new PhoneNumber3DTO(o))
                .collect(Collectors.toList());

        return phoneNumberDTOS.stream()
                .collect(Collectors.groupingBy(o -> o.getId()));
    }

    @Test
    @Order(4)
    public void 고객리스트_검색() {
        List<Customer3DTO> allCustomers = customer3QueryRepository.findAllCustomers();
        System.out.println(allCustomers);
    }
    /*
    @Test
    @Order(3)
    public void 고객삭제() {
        customerRepository.deleteById(customerId);
    }*/
}