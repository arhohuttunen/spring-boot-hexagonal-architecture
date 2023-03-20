package com.arhohuttunen.coffeeshop.application.out;

import com.arhohuttunen.coffeeshop.application.payment.Payment;

import java.util.UUID;

public interface Payments {
    Payment findPaymentByOrderId(UUID orderId);
    Payment save(Payment payment);
}
