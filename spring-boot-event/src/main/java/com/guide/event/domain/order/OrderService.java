package com.guide.event.domain.order;

import com.guide.event.domain.member.Member;
import com.guide.event.domain.member.MemberRepository;
import com.guide.event.global.config.event.Event;
import com.guide.event.global.config.event.PublishEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RequiredArgsConstructor
//@Transactional(readOnly = true)
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public OrderDTO.Response.Create order(OrderDTO.Request.Create body) {
        Member member = memberRepository.findById(body.getMemberId()).orElseThrow(() -> new EntityNotFoundException());
        Order order = Order.builder()
                .member(member)
                .itemName(body.getItemName())
                .price(body.getPrice())
                .count(body.getCount())
                .totalPrice(body.getPrice() * body.getCount())
                .build();
        Order savedOrder = orderRepository.save(order);
        return OrderMapper.INSTANCE.map(savedOrder);

    }
}