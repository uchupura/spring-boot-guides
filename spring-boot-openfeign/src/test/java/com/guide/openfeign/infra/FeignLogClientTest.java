package com.guide.openfeign.infra;

import com.guide.openfeign.domain.user.UserDTO;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootTest(
        classes = {
                FeignLogClientTest.FakeFeignConfiguration.class,
                FeignLogClientTest.FakeMessagingRestService.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeignLogClientTest {

    @Autowired
    private FeignLogClient client;

    @Test
    public void LogFeignClientTest() throws Exception {
        UserDTO.Request.Create body = UserDTO.Request.Create.builder()
                .loginId("uchupura")
                .password("init123")
                .name("홍길동")
                .phone("01000000000")
                .email("uchupura@gmail.com")
                .role("ADMIN")
                .build();

        client.createUser(body);
    }

    @RestController
    @RequestMapping(path = "/users")
    static class FakeMessagingRestService {

        @PostMapping(produces = "application/json")
        public UserDTO.Response.Create createUser(@RequestBody final UserDTO.Request.Create body) throws Exception {
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
            return result;
        }
    }

    @Configuration(proxyBeanMethods = false)
    static class FakeRibbonConfiguration {

        @LocalServerPort
        int port;

        @Bean
        public ServerList<Server> serverList() {
            return new StaticServerList<>(new Server("localhost", port));
        }
    }

    @Configuration(proxyBeanMethods = false)
    @EnableFeignClients(clients = FeignLogClient.class)
    @EnableAutoConfiguration
    @RibbonClient(
            name = "log-client",
            configuration = FeignLogClientTest.FakeRibbonConfiguration.class)
    static class FakeFeignConfiguration {}
}