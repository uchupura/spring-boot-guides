package com.guide.websocket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker   // 웹 소켓 서버를 활성화 시키는 어노테이션
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Value("${spring.rabbitmq.host}")
    private String host;

    /**
     * @registerStompEndpoints : web socket 엔드 포인트 등록
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();   // withSockJS() : 웹 소켓을 지원하지 않는 브라우저에 Fallback 옵션을 활성화
    }


    /**
     * @configureMessageBroker : configure the message broker
     * @enableSimpleBroker : in-memory message broker
     * @enableStompBrokerRelay : SimpleBroker의 기능과 외부 message broker(RabbitMQ, ActiveMQ 등)에 메시지를 전달하는 기능
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        System.out.println(">>>>>>>>>>>>>>> HOST : " + host);
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableStompBrokerRelay("/topic")
                .setRelayHost(host)
                .setClientLogin("guest")
                .setClientPasscode("guest")
                .setSystemLogin("guest")
                .setSystemPasscode("guest");
        //registry.enableSimpleBroker("/topic");
    }
    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        messageConverters.add(new MappingJackson2MessageConverter());
        messageConverters.add(new SimpleMessageConverter());
        return false;
    }

}
