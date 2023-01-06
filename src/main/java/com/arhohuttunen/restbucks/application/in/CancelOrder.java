package com.arhohuttunen.restbucks.application.in;

import java.util.UUID;

public interface CancelOrder {
    void cancelOrder(UUID orderId);
}
