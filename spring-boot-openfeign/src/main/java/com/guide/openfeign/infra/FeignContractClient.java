package com.guide.openfeign.infra;

import com.guide.openfeign.domain.user.UserDTO;
import com.guide.openfeign.global.config.FeignContractConfiguration;
import feign.Param;
import feign.RequestLine;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "contract-client", configuration = {FeignContractConfiguration.class})
public interface FeignContractClient {
    @RequestLine("POST /users")
    Response createUser(@RequestBody UserDTO.Request.Create body);
}