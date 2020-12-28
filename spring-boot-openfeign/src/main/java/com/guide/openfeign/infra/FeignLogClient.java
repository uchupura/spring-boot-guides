package com.guide.openfeign.infra;

import com.guide.openfeign.domain.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "log-client", path = "/users")
public interface FeignLogClient {
    @PostMapping
    UserDTO.Response.Create createUser(@RequestBody UserDTO.Request.Create body);
}
