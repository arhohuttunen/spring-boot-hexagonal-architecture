package com.arhohuttunen.coffeeshop.application.in;

import com.arhohuttunen.coffeeshop.application.order.Order;

import java.util.UUID;

public interface PreparingCoffee {
    Order startPreparingOrder(UUID orderId);
    Order finishPreparingOrder(UUID orderId);
}
