package com.arhohuttunen.coffeeshop.application.in;

import com.arhohuttunen.coffeeshop.application.order.Order;
import com.arhohuttunen.coffeeshop.application.payment.CreditCard;
import com.arhohuttunen.coffeeshop.application.payment.Payment;
import com.arhohuttunen.coffeeshop.application.payment.Receipt;

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
