package com.guide.event.domain.customer;

import com.guide.event.domain.common.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerTest extends BaseTest {

    @Test
    public void MapStruct테스트() throws Exception {
        Account account = Account.builder()
                .name("우쭈뿌라1")
                .age(39)
                .phone("01000000000")
                .email("uchupura@gmail.com")
                .address("경기도 하남시")
                .build();
        Record record = Record.builder()
                .name("우쭈뿌라2")
                .number(12345L)
                .build();
        CustomerDTO customerDTO = CustomerDTO.builder()
                .id(1L)
                .account(account)
                .record(record)
                .build();

        Customer customer = CustomerMapper.INSTANCE.customerDtoToCustomer(customerDTO);

        assertThat(customer.getName()).isEqualTo(record.getName());
        assertThat(customer.getPhone()).isEqualTo(account.getPhone());
        assertThat(customer.getNumber()).isEqualTo(record.getNumber());
    }
}