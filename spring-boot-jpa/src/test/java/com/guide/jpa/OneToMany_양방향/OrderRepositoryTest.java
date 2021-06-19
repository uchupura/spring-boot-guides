package com.guide.jpa.OneToMany_양방향;

import com.guide.jpa.OneToMany_양방향.dto.OrderDto;
import com.guide.jpa.OneToMany_양방향.dto.OrderItemDto;
import com.guide.jpa.OneToMany_양방향.entity.Item;
import com.guide.jpa.OneToMany_양방향.entity.Order;
import com.guide.jpa.OneToMany_양방향.entity.OrderItem;
import com.guide.jpa.OneToMany_양방향.entity.OrderStatus;
import com.guide.jpa.OneToMany_양방향.repository.ItemRepository;
import com.guide.jpa.OneToMany_양방향.repository.OrderItemRepository;
import com.guide.jpa.OneToMany_양방향.repository.OrderQueryRepository;
import com.guide.jpa.OneToMany_양방향.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@EnableJpaRepositories(basePackages = "com.guide.jpa.OneToMany_양방향")
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
        Item item2 = new Item("망원경", 20000, 50);

        // 03. OrderItem 생성
        orderItem = new OrderItem(item, order, 20000, 2);
        OrderItem orderItem2 = new OrderItem(item2, order, 20000, 2);

        orderRepository.save(order);
        itemRepository.save(item);
        itemRepository.save(item2);
        orderItemRepository.save(orderItem);
        orderItemRepository.save(orderItem2);
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

        List<Order> savedOrders = orderRepository.findAllOrderWithItem();
        System.out.println(savedOrders.size());
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
        OrderItemDto orderItemDto2 = orderQueryRepository.findOrderAndOrderItem2(order.getId());
        System.out.println("Hello");
    }

    @Test
    public void findAllWithItem() throws Exception {
        em.flush();
        em.clear();

        List<Order> order = orderQueryRepository.findAllWithItem();
        for (Order order1 : order) {
            System.out.println(order1.toString());
        }
    }
}
