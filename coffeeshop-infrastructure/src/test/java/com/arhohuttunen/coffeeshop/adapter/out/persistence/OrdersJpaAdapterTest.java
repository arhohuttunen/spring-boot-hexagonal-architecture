package com.arhohuttunen.coffeeshop.adapter.out.persistence;

import com.arhohuttunen.coffeeshop.application.order.Order;
import com.arhohuttunen.coffeeshop.application.order.OrderItem;
import com.arhohuttunen.coffeeshop.shared.Drink;
import com.arhohuttunen.coffeeshop.shared.Location;
import com.arhohuttunen.coffeeshop.shared.Milk;
import com.arhohuttunen.coffeeshop.shared.Size;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@PersistenceTest
public class OrdersJpaAdapterTest {
    @Autowired
    private OrdersJpaAdapter adapter;

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Test
    void creatingOrderReturnsPersistedOrder() {
        var order = new Order(Location.TAKE_AWAY, List.of(new OrderItem(Drink.LATTE, 1, Milk.WHOLE, Size.SMALL)));

        var persistedOrder = adapter.save(order);

        assertThat(persistedOrder.getLocation()).isEqualTo(Location.TAKE_AWAY);
        assertThat(persistedOrder.getItems()).containsExactly(new OrderItem(Drink.LATTE, 1, Milk.WHOLE, Size.SMALL));
    }

    @Test
    @Sql("classpath:data/order.sql")
    void findingPreviouslyPersistedOrderReturnsDetails() {
        var order = adapter.findOrderById(UUID.fromString("757d9c0f-400f-4137-9aea-83e64ba3efb2"));

        assertThat(order.getLocation()).isEqualTo(Location.IN_STORE);
        assertThat(order.getItems()).containsExactly(new OrderItem(Drink.ESPRESSO, 1, Milk.SKIMMED, Size.LARGE));
    }

    @Test
    void findingNonExistingOrderThrowsException() {
        assertThatThrownBy(() -> adapter.findOrderById(UUID.randomUUID())).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @Sql("classpath:data/order.sql")
    void deletesPreviouslyPersistedOrder() {
        adapter.deleteById(UUID.fromString("757d9c0f-400f-4137-9aea-83e64ba3efb2"));

        assertThat(orderJpaRepository.findById(UUID.fromString("757d9c0f-400f-4137-9aea-83e64ba3efb2"))).isEmpty();
    }
}
