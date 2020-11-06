package com.guide.restdocs.controller;

import com.guide.restdocs.domain.User;
import com.guide.restdocs.domain.User.CreateUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    @PostMapping
    public CreateUser createUser(@RequestBody CreateUser users) {

        return users;
    }
}
