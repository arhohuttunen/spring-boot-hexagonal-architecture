package com.arhohuttunen.restbucks.application.out;

import com.arhohuttunen.restbucks.application.order.Order;

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
