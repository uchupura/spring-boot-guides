package com.guide.service;

import com.guide.common.enumtype.ExceptionType;
import com.guide.common.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomServiceTest {
    @Autowired
    private CustomService customService;

    @MockBean
    private MockService mockService;

    @DisplayName("서비스 Exception에 대한 에러 처리 테스트")
    @Test
    void 예외처리_테스트() {
        BusinessException businessException = assertThrows(BusinessException.class, () -> customService.getToken());
        Assertions.assertEquals(ExceptionType.USER_NOT_FOUND, businessException.getType());
    }

    @Test
    void 서비스_MockBean() {
        when(mockService.send())
                .thenReturn("receive is called");

        String res = mockService.send();
        Assertions.assertEquals("receive is called", res);
    }
}