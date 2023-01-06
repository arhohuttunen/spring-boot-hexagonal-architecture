package com.arhohuttunen.restbucks.application.in;

import com.arhohuttunen.restbucks.application.payment.Receipt;

import java.util.UUID;

public interface ReadReceipt {
    Receipt readReceipt(UUID orderId);
}
