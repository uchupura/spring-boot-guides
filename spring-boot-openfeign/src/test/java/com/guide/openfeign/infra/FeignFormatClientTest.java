package com.guide.openfeign.infra;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@SpringBootTest(
        classes = {
                FeignFormatClientTest.FakeFeignConfiguration.class,
                FeignFormatClientTest.FakeMessagingRestService.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeignFormatClientTest {

    @Autowired
    private FeignFormatClient client;

    @Test
    public void 날짜_형식_테스트() throws Exception {
        client.search(LocalDate.now().minusDays(1), LocalDate.now());
    }

    @RestController
    @RequestMapping(path = "/search")
    static class FakeMessagingRestService {
        @GetMapping
        String search(@RequestParam("startDate") String from, @RequestParam("endDate") String end) {
            return "START : " + from + ", END : " + end;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @EnableFeignClients(clients = FeignFormatClient.class)
    @EnableAutoConfiguration
    @RibbonClient(
            name = "format-client",
            configuration = FeignFormatClientTest.FakeRibbonConfiguration.class)
    static class FakeFeignConfiguration {}

    @Configuration(proxyBeanMethods = false)
    static class FakeRibbonConfiguration {

        @LocalServerPort
        int port;

        @Bean
        public ServerList<Server> serverList() {
            return new StaticServerList<>(new Server("localhost", port));
        }
    }
}