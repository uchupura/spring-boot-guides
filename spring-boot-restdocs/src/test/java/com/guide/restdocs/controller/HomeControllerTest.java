package com.guide.restdocs.controller;

import com.guide.restdocs.common.BaseTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class HomeControllerTest extends BaseTest {

    private final String RESOURCE = "Home/";

    @BeforeAll
    public void setup() {
        System.out.println(">>>>>>>>>> @BeforAll is called!!!");
    }

    @Test
    void greeting() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World")))
                .andDo(document(RESOURCE + "{method-name}",
                        responseFields(
                            fieldWithPath("message").description("The welcome message for the user.<<common, World>>").optional()
                        )
                ));
    }

    @Test
    void greeting2() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World")))
                .andDo(document(RESOURCE + "{method-name}",
                        responseFields(
                                fieldWithPath("message").description("The welcome message for the user.<<common, World>>").optional()
                        )
                ));
    }
}