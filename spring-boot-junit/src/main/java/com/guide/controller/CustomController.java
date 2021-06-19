package com.guide.controller;

import com.guide.common.enumtype.ExceptionType;
import com.guide.common.exception.BusinessException;
import com.guide.common.model.CommonApiResponse;
import com.guide.model.User;
import com.guide.service.MockService;
import com.guide.template.TemplateClass;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomController {
    private final MockService mockService;

    @GetMapping("/hello")
    public String hello() {
        return "world!!";
    }

    @GetMapping("/world")
    public CommonApiResponse world() {
        throw new BusinessException(ExceptionType.UNAUTHORIZED_USER);
    }

    @GetMapping("/mock")
    public String mock() {
        String res = mockService.send();
        return res;
    }

    @GetMapping("/template")
    public User template() {
        TemplateClass<User> userTemplateClass = new TemplateClass<>();
        User user = new User("uchupura");
        return userTemplateClass.getData(1l, user);
    }
}
