package com.guide.jpa.OneToMany_일반;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class OrderRepositoryTest {
    private Order order;
    private OrderItem orderItem;
    @Autowired
    private EntityManager em;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderQueryRepository orderQueryRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @BeforeEach
    public void init() {
        // 01. Order 생성
        order = new Order(LocalDateTime.now(), OrderStatus.ORDER);

        // 02. Item 생성
        Item item = new Item("장난감", 10000, 50);

        // 03. OrderItem 생성
        orderItem = new OrderItem(item, order, 20000, 2);

        orderRepository.save(order);
        itemRepository.save(item);
        orderItemRepository.save(orderItem);
    }

    @Test
    public void 주문_생성() throws Exception {
        assertEquals(1, orderRepository.count());
    }

    @Test
    public void 주문_검색_LAZY() throws Exception {
        em.flush();
        em.clear();

        Order savedOrder = orderRepository.findById(this.order.getId()).orElseThrow(() -> new RuntimeException("Not Found"));
        System.out.println(savedOrder.getOrderItems().get(0).getId());
    }

    @Test
    public void 주문_검색_FETCH() throws Exception {
        em.flush();
        em.clear();

        Order savedOrder = orderRepository.findOrder(this.order.getId());
        System.out.println(savedOrder.getOrderItems().get(0).getId());
    }

    @Test
    public void 주문_아이템_검색_FETCH() throws Exception {
        em.flush();
        em.clear();

        Order savedOrder = orderRepository.findOrderWithItem(this.order.getId());
        System.out.println(savedOrder.getOrderItems().get(0).getId());
    }

    @Test
    public void 주문_아이템_검색() throws Exception {
        em.flush();
        em.clear();

        OrderDto orderDto1 = orderQueryRepository.findOrderItem(order.getId());
        System.out.println("Hello");

        OrderDto orderDto2 = orderRepository.findOrderItem(order.getId());
        System.out.println("Hello");
    }

    @Test
    public void 아이템_검색() throws Exception {
        em.flush();
        em.clear();

        OrderItemDto orderItemDto = orderQueryRepository.findOrderAndOrderItem(order.getId());
        System.out.println("Hello");
    }
}
