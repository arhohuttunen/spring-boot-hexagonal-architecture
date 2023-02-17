package com.arhohuttunen.restbucks.application.in;

import com.arhohuttunen.restbucks.application.order.Order;
import com.arhohuttunen.restbucks.application.payment.CreditCard;
import com.arhohuttunen.restbucks.application.payment.Payment;
import com.arhohuttunen.restbucks.application.payment.Receipt;

import java.util.UUID;

public interface OrderingCoffee {
    Order createOrder(Order order);
    Order readOrder(UUID orderId);
    Order updateOrder(UUID orderId, Order order);
    void cancelOrder(UUID orderId);
    Payment payOrder(UUID orderId, CreditCard creditCard);
    Receipt readReceipt(UUID orderId);
    Order completeOrder(UUID orderId);
}
