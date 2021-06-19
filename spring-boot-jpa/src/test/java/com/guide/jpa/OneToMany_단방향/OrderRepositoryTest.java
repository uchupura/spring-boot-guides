package com.guide.jpa.OneToMany_단방향;

import com.guide.jpa.OneToMany_단방향.dto.OrderItemDto;
import com.guide.jpa.OneToMany_단방향.entity.Item;
import com.guide.jpa.OneToMany_단방향.entity.Order;
import com.guide.jpa.OneToMany_단방향.entity.OrderItem;
import com.guide.jpa.OneToMany_단방향.entity.OrderStatus;
import com.guide.jpa.OneToMany_단방향.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Rollback(value = false)
@EnableJpaRepositories(basePackages = "com.guide.jpa.OneToMany_단방향")
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
    @Autowired
    private ItemQueryRepository itemQueryRepository;

    @BeforeEach
    public void init() {
        // 01. Order 생성
        order = new Order(LocalDateTime.now(), OrderStatus.ORDER);
        orderRepository.save(order);

        // 02. Item 생성
        Item item = new Item("장난감", 10000, 50);
        itemRepository.save(item);

        // 03. OrderItem 생성
        orderItem = new OrderItem(item, order, 20000, 2);
        orderItemRepository.save(orderItem);

        item = new Item("물티슈", 1000, 100);
        itemRepository.save(item);

        orderItem = new OrderItem(item, order, 2000, 2);
        orderItemRepository.save(orderItem);
    }

    @Test
    public void 주문_생성() throws Exception {
        assertEquals(1, orderRepository.count());
    }

    @Test
    public void 주문_조회() throws Exception {
        em.flush();
        em.clear();
        List<Order> orders = orderRepository.findAll();
        List<OrderItem> orderItems = orderItemRepository.findOrderItemsByOrderIn(orders);
        List<OrderItemDto> orderItemDtos = orderItems.stream()
                .map(o -> new OrderItemDto(o))
                .collect(Collectors.toList());
    }

    @Test
    void 아이템_조회() {
        Item item = itemQueryRepository.findItem(1l);
        Assertions.assertEquals("장난감", item.getName());
    }
}
