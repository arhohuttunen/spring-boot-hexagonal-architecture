package com.arhohuttunen.restbucks.application.in;

import com.arhohuttunen.restbucks.application.payment.CreditCard;
import com.arhohuttunen.restbucks.application.payment.Payment;

import java.util.UUID;

public interface PayOrder {
    Payment payOrder(UUID orderId, CreditCard creditCard);
}
