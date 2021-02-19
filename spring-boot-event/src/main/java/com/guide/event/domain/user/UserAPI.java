package com.guide.event.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(UserAPI.URL)
public class UserAPI {
    public static final String URL = "/v1/users";

    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody User body) {
        return userService.createUser(body);
    }

    @PutMapping
    public User updateUser(@RequestBody User body) {
        return userService.updateUser(body);
    }
}
