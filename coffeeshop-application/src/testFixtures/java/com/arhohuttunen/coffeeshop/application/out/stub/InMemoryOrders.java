package com.arhohuttunen.coffeeshop.application.out.stub;

import com.arhohuttunen.coffeeshop.application.order.Order;
import com.arhohuttunen.coffeeshop.application.out.Orders;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryOrders implements Orders {
    private final Map<UUID, Order> entities = new HashMap<>();

    @Override
    public Order findOrderById(UUID orderId) {
        return entities.get(orderId);
    }

    @Override
    public Order save(Order order) {
        entities.put(order.getId(), order);
        return order;
    }

    @Override
    public void deleteById(UUID orderId) {
        entities.remove(orderId);
    }
}
