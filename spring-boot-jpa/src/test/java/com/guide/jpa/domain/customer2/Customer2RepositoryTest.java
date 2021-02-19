package com.guide.jpa.domain.customer2;

import com.guide.jpa.domain.customer.PhoneNumber;
import com.guide.jpa.domain.customer.PhoneType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Rollback(value = false)
@Transactional
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Customer2RepositoryTest {
    private Long customerId = null;
    @Autowired
    private EntityManager em;

    @Autowired
    private Customer2Repository customer2Repository;

    @Autowired
    private PhoneNumber2Repository phoneNumber2Repository;

    @Autowired
    private CardRepository cardRepository;

    @Test
    @Order(1)
    public void 고객등록() {
        Customer2 customer = new Customer2("홍길동");
        Customer2 savedCustomer = customer2Repository.save(customer);

        PhoneNumber2 phoneNumber1 = new PhoneNumber2("0317091111", PhoneType.House, customer);
        phoneNumber2Repository.save(phoneNumber1);

        PhoneNumber2 phoneNumber2 = new PhoneNumber2("01077777777", PhoneType.Cell, customer);
        phoneNumber2Repository.save(phoneNumber2);

        Card card1 = new Card("card1", customer);
        cardRepository.save(card1);

        Card card2 = new Card("card2", customer);
        cardRepository.save(card2);


        Customer2 customer2 = new Customer2("홍길동2");
        Customer2 savedCustomer2 = customer2Repository.save(customer2);

        PhoneNumber2 phoneNumber2_1 = new PhoneNumber2("0317091111", PhoneType.House, customer2);
        phoneNumber2Repository.save(phoneNumber2_1);

        PhoneNumber2 phoneNumber2_2 = new PhoneNumber2("01077777777", PhoneType.Cell, customer2);
        phoneNumber2Repository.save(phoneNumber2_2);

        Card card3 = new Card("card3", customer2);
        cardRepository.save(card3);

        Card card4 = new Card("card4", customer2);
        cardRepository.save(card4);



        Customer2 customer3 = new Customer2("홍길동3");
        Customer2 savedCustomer3 = customer2Repository.save(customer3);

        PhoneNumber2 phoneNumber3_1 = new PhoneNumber2("0317091111", PhoneType.House, customer3);
        phoneNumber2Repository.save(phoneNumber3_1);

        PhoneNumber2 phoneNumber3_2 = new PhoneNumber2("01077777777", PhoneType.Cell, customer3);
        phoneNumber2Repository.save(phoneNumber3_2);

        Card card5 = new Card("card5", customer3);
        cardRepository.save(card5);

        Card card6 = new Card("card6", customer3);
        cardRepository.save(card6);



        Customer2 customer4 = new Customer2("홍길동4");
        Customer2 savedCustomer4 = customer2Repository.save(customer4);

        PhoneNumber2 phoneNumber4_1 = new PhoneNumber2("0317091111", PhoneType.House, customer4);
        phoneNumber2Repository.save(phoneNumber4_1);

        PhoneNumber2 phoneNumber4_2 = new PhoneNumber2("01077777777", PhoneType.Cell, customer4);
        phoneNumber2Repository.save(phoneNumber4_2);

        Card card7 = new Card("card7", customer4);
        cardRepository.save(card7);

        Card card8 = new Card("card8", customer4);
        cardRepository.save(card8);

        customerId = savedCustomer.getId();
    }

    @Test
    @Order(2)
    public void 고객검색() {
        Customer2 customer = customer2Repository.findById(customerId).orElseThrow(() -> new RuntimeException());
        Customer2DTO customer2DTO = new Customer2DTO(customer);
    }

    @Test
    @Order(3)
    public void 고객리스트검색() {
        List<Customer2> customers = customer2Repository.findAll();
        List<Customer2DTO> customerDTOS = customers.stream()
                .map(customer -> new Customer2DTO(customer))
                .collect(Collectors.toList());
        customerDTOS.forEach(customer -> {
            System.out.println(customer.toString());
        });
        System.out.println("END!!");
    }

    /*
    @Test
    @Order(3)
    public void 고객삭제() {
        customerRepository.deleteById(customerId);
    }*/
}