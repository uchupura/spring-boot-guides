package com.guide.openfeign.infra;

import com.guide.openfeign.domain.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "pageable-client", path = "/users")
public interface FeignPageableClient {
    @GetMapping
    UserDTO.Response.Users search(PageRequest pageable);
}
