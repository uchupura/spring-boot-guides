package com.guide.websocket.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class WebSocketClient {

    @Value("${spring.web.socket.host:localhost}")
    private String host;

    @Value("${spring.web.socket.port:8080}")
    private String port;

    @Value("${spring.web.socket.endpoint:ws}")
    private String endpoint;

    private final static WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

    private ConcurrentLinkedDeque<StompSession> pool = new ConcurrentLinkedDeque<>();

    private void destroyPool() {
        pool.clear();
    }
    public StompSession connect() throws ExecutionException, InterruptedException {
        if(pool.size() < 5) return create();

        StompSession session = pool.poll();
        if (!session.isConnected()) {
            destroyPool();
            return create();
        }

        return session;
    }
    public void close(StompSession session) {
        pool.add(session);
    }

    public StompSession create() throws ExecutionException, InterruptedException {

        Transport webSocketTransport = new WebSocketTransport(new StandardWebSocketClient());
        List<Transport> transports = Collections.singletonList(webSocketTransport);

        SockJsClient sockJsClient = new SockJsClient(transports);

        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StringBuffer url = new StringBuffer("http://{host}:{port}/")
                .append(endpoint);

        return stompClient.connect(url.toString(), headers, new StompSessionHandler(), host, port).get();
    }

    private class StompSessionHandler extends StompSessionHandlerAdapter {
        @Override
        public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
            log.info(">>>>> New session: {}", stompSession.getSessionId());
        }

        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
            log.error("{}", exception);
        }
    }
}
