package com.arhohuttunen.coffeeshop.application.out;

import com.arhohuttunen.coffeeshop.application.order.Order;

import java.util.UUID;

public interface Orders {
    Order findOrderById(UUID orderId);
    Order save(Order order);
    void deleteById(UUID orderId);
}
