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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootTest(
        classes = {
                FeignPageableClientTest.FakeFeignConfiguration.class,
                FeignPageableClientTest.FakeMessagingRestService.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeignPageableClientTest {

    @Autowired
    FeignPageableClient client;

    @Test
    public void 페이징_테스트() throws Exception {
        client.search(PageRequest.of(4, 30));
    }

    @RestController
    @RequestMapping(path = "/users")
    static class FakeMessagingRestService {

        @GetMapping(produces = "application/json")
        public UserDTO.Response.Users search(Pageable pageable) throws Exception {
            UserDTO.Response.Users users = new UserDTO.Response.Users();
            return users;
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
    @EnableFeignClients(clients = FeignPageableClient.class)
    @EnableAutoConfiguration
    @RibbonClient(
            name = "pageable-client",
            configuration = FeignPageableClientTest.FakeRibbonConfiguration.class)
    static class FakeFeignConfiguration {}
}