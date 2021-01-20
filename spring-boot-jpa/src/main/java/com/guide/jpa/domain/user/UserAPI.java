package com.guide.jpa.domain.user;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(UserAPI.URL)
public class UserAPI {
    public static final String URL = "/users";

    private final UserApiService userApiService;

    @PostMapping
    public UserDTO.Response.Create create(@RequestBody  UserDTO.Request.Create body) {
        return userApiService.register(body);
    }

    @GetMapping("{id}")
    public UserDTO.Response.User get(@PathVariable("id") String id) {
        return userApiService.get(id);
    }
}
