package com.arhohuttunen.coffeeshop.application;

import com.arhohuttunen.architecture.UseCase;
import com.arhohuttunen.coffeeshop.application.in.PreparingCoffee;
import com.arhohuttunen.coffeeshop.application.out.Orders;

import java.util.UUID;

@UseCase
public class CoffeeMachine implements PreparingCoffee {
    private final Orders orders;

    public CoffeeMachine(Orders orders) {
        this.orders = orders;
    }

    @Override
    public void startPreparingOrder(UUID orderId) {
        var order = orders.findOrderById(orderId);

        orders.save(order.markBeingPrepared());
    }

    @Override
    public void finishPreparingOrder(UUID orderId) {
        var order = orders.findOrderById(orderId);

        orders.save(order.markPrepared());
    }
}
