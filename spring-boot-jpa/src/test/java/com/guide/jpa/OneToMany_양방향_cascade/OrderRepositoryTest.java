package com.guide.jpa.OneToMany_양방향_cascade;

import com.guide.jpa.OneToMany_양방향_cascade.entity.Item;
import com.guide.jpa.OneToMany_양방향_cascade.entity.Order;
import com.guide.jpa.OneToMany_양방향_cascade.entity.OrderItem;
import com.guide.jpa.OneToMany_양방향_cascade.repository.ItemRepository;
import com.guide.jpa.OneToMany_양방향_cascade.repository.OrderItemRepository;
import com.guide.jpa.OneToMany_양방향_cascade.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Rollback(value = false)
@EnableJpaRepositories(basePackages = "com.guide.jpa.OneToMany_양방향_cascade")
@Transactional
@SpringBootTest
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ItemRepository itemRepository;
    @BeforeEach
    public void init() {
        Item 장난감 = Item.createItem("장난감", 10000, 50);
        itemRepository.save(장난감);
        Item 물티슈 = Item.createItem("물티슈", 1000, 100);
        itemRepository.save(물티슈);

        OrderItem 장난감아이템 = OrderItem.createOrderItem(장난감, 20000, 2);
        orderItemRepository.save(장난감아이템);
        OrderItem 물티슈아이템 = OrderItem.createOrderItem(물티슈, 2000, 2);
        orderItemRepository.save(물티슈아이템);

        Order 주문 = Order.createOrder(장난감아이템, 물티슈아이템);
        orderRepository.save(주문);
    }

    @Test
    void 테스트() {

    }
}
