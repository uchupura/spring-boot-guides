package com.guide.openfeign.infra;

import com.guide.openfeign.domain.message.Message;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = {
                MessagingRestClientTest.FakeFeignConfiguration.class,
                MessagingRestClientTest.FakeMessagingRestService.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessagingRestClientTest {

    @Autowired
    MessagingRestClient client;

    @Test
    public void getMessage() {
        final Message response = client.getMessage("Foo");
        assertThat(response.getText()).isEqualTo("Hello, Foo");
    }

    @Test
    public void setMessage() {
        final Message message = new Message();
        message.setText("Hello");
        final Message response = client.setMessage("Foo", message);
        assertThat(response.getText()).isEqualTo("Hi, Foo");
    }

    @RestController
    @RequestMapping(path = "/messaging")
    static class FakeMessagingRestService {

        @GetMapping(params = {"name"}, produces = "application/json")
        public String getMessage(@RequestParam("name") final String name) {
            assertThat(name).isEqualTo("Foo");
            return "{\"text\":\"Hello, Foo\"}";
        }

        @PostMapping(params = {"name"}, produces = "application/json")
        public String setMessage(@RequestParam("name") final String name, @RequestBody final String message) throws Exception {
            assertThat(name).isEqualTo("Foo");
            JSONAssert.assertEquals(message, "{ \"text\":\"Hello\" }", false);
            return "{\"text\":\"Hi, Foo\"}";
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
    @EnableFeignClients(clients = MessagingRestClient.class)
    @EnableAutoConfiguration
    @RibbonClient(
            name = "messagingRestClient",
            configuration = MessagingRestClientTest.FakeRibbonConfiguration.class)
    static class FakeFeignConfiguration {}
}