package com.guide.openfeign.infra;

import com.guide.openfeign.domain.user.UserDTO;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import feign.RetryableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SpringBootTest(
        classes = {
                FeignRetryClientTest.FakeFeignConfiguration.class,
                FeignRetryClientTest.FakeMessagingRestService.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeignRetryClientTest {

    @Autowired
    private FeignRetryClient client;

    @Test
    public void 연속실패_케이스() throws Exception {
        Assertions.assertThrows(RetryableException.class, () -> {
            UserDTO.Request.Create body = UserDTO.Request.Create.builder()
                    .loginId("uchupura")
                    .password("init123")
                    .name("홍길동")
                    .phone("01000000000")
                    .email("uchupura@gmail.com")
                    .role("ADMIN")
                    .build();

            client.createUser(body);
        });
    }

    @Test
    public void 실패후_성공하는_케이스() throws Exception {
        UserDTO.Request.Create body = UserDTO.Request.Create.builder()
                .loginId("uchupura")
                .password("init123")
                .name("홍길동")
                .phone("01000000000")
                .email("uchupura@gmail.com")
                .role("ADMIN")
                .build();

        client.updateUser(body);
    }

    @RestController
    @RequestMapping(path = "/users")
    static class FakeMessagingRestService {
        int retry = 0;
        @PostMapping(produces = "application/json")
        public ResponseEntity createUser(@RequestBody final UserDTO.Request.Create body) throws Exception {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        @PutMapping(produces = "application/json")
        public ResponseEntity updateUser(@RequestBody final UserDTO.Request.Create body) throws Exception {
            retry++;
            if (retry % 2 == 0) {
                UserDTO.Response.Create result = UserDTO.Response.Create.builder()
                        .id(1L)
                        .loginId(body.getLoginId())
                        .password(body.getPassword())
                        .name(body.getName())
                        .phone(body.getPhone())
                        .email(body.getEmail())
                        .role(body.getRole())
                        .createdDate(body.getCreatedDate())
                        .build();
                return new ResponseEntity(result, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Configuration(proxyBeanMethods = false)
    static class FakeRibbonConfiguration {

        @LocalServerPort int port;

        @Bean
        public ServerList<Server> serverList() {
            return new StaticServerList<>(new Server("localhost", port));
        }
    }

    @Configuration(proxyBeanMethods = false)
    @EnableFeignClients(clients = FeignRetryClient.class)
    @EnableAutoConfiguration
    @RibbonClient(
            name = "retry-client",
            configuration = FeignRetryClientTest.FakeRibbonConfiguration.class)
    static class FakeFeignConfiguration {}
}