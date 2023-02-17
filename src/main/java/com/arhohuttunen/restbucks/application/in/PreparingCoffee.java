package com.arhohuttunen.restbucks.application.in;

import java.util.UUID;

public interface PreparingCoffee {
    void startPreparingOrder(UUID orderId);
    void finishPreparingOrder(UUID orderId);
}
