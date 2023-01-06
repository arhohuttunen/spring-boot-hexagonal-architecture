package com.arhohuttunen.restbucks.application.in;

import com.arhohuttunen.restbucks.application.order.Order;

import java.util.UUID;

public interface UpdateOrder {
    Order updateOrder(UUID orderId, Order order);
}
