package com.guide.https.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(HelloAPI.URL)
public class HelloAPI {
    public static final String URL = "/hello";

    @GetMapping
    public String hello() {
        return "Hello World!";
    }
}