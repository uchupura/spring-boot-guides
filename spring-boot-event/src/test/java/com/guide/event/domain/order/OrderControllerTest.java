package com.guide.event.domain.order;

import com.guide.event.domain.common.BaseTest;
import com.guide.event.domain.member.MemberDTO;
import com.guide.event.domain.member.MemberService;
import com.guide.event.domain.model.Address;
import com.guide.event.global.common.CommonApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class OrderControllerTest extends BaseTest {
    @Autowired
    private MemberService memberService;

    @Test
    public void TEST01_주문() throws Exception {
        Long memberId = null;
        if (!memberService.hasMember("default")) {
            memberId = createMember();
        }

        OrderDTO.Request.Create body = OrderDTO.Request.Create.builder()
                .memberId(memberId)
                .itemName("죠리퐁")
                .price(1500)
                .count(3)
                .build();

        MvcResult result = this.mockMvc
                .perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andReturn();
        if (memberId != null) {
            deleteMember(memberId);
        }
    }

    @Test
    public void TEST02_주문() throws Exception {
        OrderDTO.Request.Create body = OrderDTO.Request.Create.builder()
                .memberId(0L)
                .itemName("죠리퐁")
                .price(1500)
                .count(3)
                .build();

        MvcResult result = this.mockMvc
                .perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andReturn();
        CommonApiResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), CommonApiResponse.class);
        assertThat(response.getCode()).isEqualTo("C500");

    }

    private Long createMember() {
        Address address = Address.builder()
                .city("하남시")
                .street("하남대로")
                .zipcode("xxxxx")
                .build();
        MemberDTO.Request.Create body = MemberDTO.Request.Create.builder()
                .name("default")
                .phone("01047071628")
                .email("default@gmail.com")
                .password("Init123!!")
                .address(address)
                .build();
        MemberDTO.Response.Create member = memberService.createMember(body);
        return member.getId();
    }

    private void deleteMember(Long id) {
        memberService.deleteMember(id);
    }
}