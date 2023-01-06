package com.arhohuttunen.restbucks.application.out;

import com.arhohuttunen.restbucks.application.order.Order;

import java.util.UUID;

public interface Orders {
    Order findOrderById(UUID orderId);
    Order save(Order order);
    void deleteById(UUID orderId);
}
