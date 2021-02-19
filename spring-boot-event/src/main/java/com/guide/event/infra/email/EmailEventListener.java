package com.guide.event.infra.email;

import com.guide.event.domain.order.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Service
public class EmailEventListener {

    private final EmailService emailService;

    @Async
    @TransactionalEventListener
    public void handle1(OrderCreatedEvent event) {
        System.out.println( "trn" + event.getOrder().getId());
        //throw new RuntimeException("예외 발생");
        emailService.send(event.getOrder().getId(), event.getOrder().getMember().getName(), event.getOrder().getTotalPrice());
    }


//    @EventListener
//    public void handle2(OrderCreatedEvent event) {
//        System.out.println("el" + event.getOrder().getId());
//        throw new RuntimeException("예외 발생");
//    }
}
