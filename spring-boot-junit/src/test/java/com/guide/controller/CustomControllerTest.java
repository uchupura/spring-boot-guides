package com.guide.controller;

import com.guide.common.exception.GlobalExceptionHandler;
import com.guide.service.MockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
class CustomControllerTest {
    private MockMvc mock;

    @Mock
    private MockService mockService;
    @InjectMocks
    private CustomController customController;

    @BeforeEach
    void init() {
        mock = MockMvcBuilders.standaloneSetup(customController).setControllerAdvice(GlobalExceptionHandler.class).build();
    }
    @Test
    @DisplayName("HTTP Status Code를 통한 에러 테스트")
    void API_에러_01() throws Exception {
        MvcResult result = this.mock.perform(get("/hello"))
//                .andExpect(status().isBadRequest())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("HTTP Response의 code 필드를 통한 에러 테스트")
    void API_에러_02() throws Exception {
        MvcResult result = this.mock.perform(get("/world"))
                .andExpect(jsonPath("$.code").value("SECURITY001"))
                .andReturn();
    }

    @Test
    @DisplayName("HTTP Response의 code 필드를 통한 에러 테스트")
    void API_에러_03() throws Exception {
        /*MockService mockService = mock(MockService.class);
        when(mockService.send()).thenReturn("receive is called");*/
        Mockito.when(mockService.send())
                .thenReturn("receive is called");

        MvcResult result = this.mock.perform(get("/mock"))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}