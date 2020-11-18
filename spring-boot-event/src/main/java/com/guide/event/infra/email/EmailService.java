package com.guide.event.infra.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {
    public void send(Long orderId, String memberName, int totalPrice) {
        log.info(">>>>> Send Email {}, {}, {}", orderId, memberName, totalPrice);
    }
}
