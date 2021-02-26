package com.guide.oauth2.password.auth.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(UserController.URL)
public class UserController {
    public static final String URL = "/v1/users";

    @GetMapping
    public String hello() {
        return "hello";
    }
}