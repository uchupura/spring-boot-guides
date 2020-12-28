package com.guide.openfeign.infra;

import com.guide.openfeign.domain.user.UserDTO;
import com.guide.openfeign.global.config.FeignRetryConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "retry-client", path="/users", configuration = FeignRetryConfiguration.class)
public interface FeignRetryClient {
    @PostMapping
    UserDTO.Response.Create createUser(@RequestBody UserDTO.Request.Create body);

    @PutMapping
    UserDTO.Response.Create updateUser(@RequestBody UserDTO.Request.Create body);
}
