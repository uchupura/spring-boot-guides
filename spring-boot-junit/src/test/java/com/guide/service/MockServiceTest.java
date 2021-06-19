package com.guide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class MockServiceTest {

    @Test
    void 테스트() {
        MockService mockService = mock(MockService.class);
        when(mockService.receive()).thenReturn("send is called");

        Assertions.assertEquals("send is called", mockService.receive());
    }
}