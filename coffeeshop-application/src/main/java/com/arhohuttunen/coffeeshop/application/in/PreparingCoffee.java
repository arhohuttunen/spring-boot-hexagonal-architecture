package com.arhohuttunen.coffeeshop.application.in;

import java.util.UUID;

public interface PreparingCoffee {
    void startPreparingOrder(UUID orderId);
    void finishPreparingOrder(UUID orderId);
}
