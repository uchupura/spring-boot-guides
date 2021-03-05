package com.guide.oauth2.resource.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(HelloController.URL)
public class HelloController {
    public static final String URL = "/v1/hello";

    @GetMapping
    public String hello() {
        return "Hello World!!";
    }
}