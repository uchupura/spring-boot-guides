package com.guide.service;

import org.springframework.stereotype.Service;

@Service
public class MockService {
    public String send() {
        return "send is called";
    }

    public String receive() {
        return "receive is called";
    }
}
