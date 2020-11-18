package com.guide.event.domain.member;

import com.guide.event.domain.common.BaseTest;
import com.guide.event.domain.model.Address;
import com.guide.event.global.common.CommonApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class MemberAPITest extends BaseTest {
    @Autowired
    private MemberService memberService;

    @Test
    public void TEST01_멤버생성() throws Exception {
        Address address = Address.builder()
                .city("하남시")
                .street("하남대로")
                .zipcode("xxxxx")
                .build();
        MemberDTO.Request.Create body = MemberDTO.Request.Create.builder()
                                            .name("uchupura")
                                            .phone("01047071628")
                                            .email("uchupura@gmail.com")
                                            .password("Jang3827!")
                                            .address(address)
                                            .build();

        MvcResult result = this.mockMvc
                .perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andReturn();
        CommonApiResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), CommonApiResponse.class);
        memberService.deleteMember(((MemberDTO.Response.Create)response.getData()).getId());
    }

    @Test
    public void TEST02_멤버생성_휴대폰_INVALID() throws Exception {
        Address address = Address.builder()
                .city("하남시")
                .street("하남대로")
                .zipcode("xxxxx")
                .build();
        MemberDTO.Request.Create body = MemberDTO.Request.Create.builder()
                .name("uchupura")
                .phone("1047071628")
                .email("uchupura@gmail.com")
                .password("Jang3827!")
                .address(address)
                .build();

        MvcResult result = this.mockMvc
                .perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andReturn();
        CommonApiResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), CommonApiResponse.class);
        assertThat(response.getCode()).isEqualTo("C500");
    }

    @Test
    public void TEST03_멤버생성_패스워드_INVALID() throws Exception {
        Address address = Address.builder()
                .city("하남시")
                .street("하남대로")
                .zipcode("xxxxx")
                .build();
        MemberDTO.Request.Create body = MemberDTO.Request.Create.builder()
                .name("uchupura")
                .phone("01047071628")
                .email("uchupura@gmail.com")
                .password("jang3827!")
                .address(address)
                .build();

        MvcResult result = this.mockMvc
                .perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andReturn();
        CommonApiResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), CommonApiResponse.class);
        assertThat(response.getCode()).isEqualTo("C500");
    }
}