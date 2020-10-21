package com.guide.oauth2.controller;

import com.guide.oauth2.config.resolver.PostPageable;
import com.guide.oauth2.config.resolver.RequestBodyWithPageable;
import com.guide.oauth2.dto.Filter;
import com.guide.oauth2.dto.UserDto;
import com.guide.oauth2.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(UserController.URI)
public class UserController extends BaseController<UserDto, Long> {

    public static final String URI = "/api/users";

    protected UserController(UserService userService) {
        super(URI, userService);
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("search")
    public Page<UserDto> search(@RequestBodyWithPageable Filter filter, @PostPageable Pageable pageable) {
        return ((UserService)service).search(filter, pageable);
    }
}
