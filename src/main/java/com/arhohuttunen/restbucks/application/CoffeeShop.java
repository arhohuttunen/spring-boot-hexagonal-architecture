package com.arhohuttunen.restbucks.application;

import com.arhohuttunen.restbucks.application.in.CancelOrder;
import com.arhohuttunen.restbucks.application.in.CompleteOrder;
import com.arhohuttunen.restbucks.application.in.CreateOrder;
import com.arhohuttunen.restbucks.application.in.PayOrder;
import com.arhohuttunen.restbucks.application.in.FinishPreparingOrder;
import com.arhohuttunen.restbucks.application.in.ReadOrder;
import com.arhohuttunen.restbucks.application.in.ReadReceipt;
import com.arhohuttunen.restbucks.application.in.StartPreparingOrder;
import com.arhohuttunen.restbucks.application.in.UpdateOrder;
import com.arhohuttunen.restbucks.application.order.Order;
import com.arhohuttunen.restbucks.application.out.Orders;
import com.arhohuttunen.restbucks.application.out.Payments;
import com.arhohuttunen.restbucks.application.payment.CreditCard;
import com.arhohuttunen.restbucks.application.payment.Payment;
import com.arhohuttunen.restbucks.application.payment.Receipt;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CoffeeShop implements CreateOrder, ReadOrder, UpdateOrder, CancelOrder, PayOrder, ReadReceipt, StartPreparingOrder, FinishPreparingOrder, CompleteOrder {
    private final Orders orders;
    private final Payments payments;

    @Override
    @Transactional
    public Order createOrder(Order order) {
        return orders.save(order);
    }

    @Override
    @Transactional
    public Order readOrder(UUID orderId) {
        return orders.findOrderById(orderId);
    }

    @Override
    @Transactional
    public Order updateOrder(UUID orderId, Order order) {
        var existingOrder = orders.findOrderById(orderId);

        return orders.save(existingOrder.update(order));
    }

    @Override
    @Transactional
    public void cancelOrder(UUID orderId) {
        var order = orders.findOrderById(orderId);

        if (!order.canBeCancelled()) {
            throw new IllegalStateException("Order is already paid");
        }

        orders.deleteById(orderId);
    }

    @Override
    @Transactional
    public Payment payOrder(UUID orderId, CreditCard creditCard) {
        var order = orders.findOrderById(orderId);

        orders.save(order.markPaid());

        return payments.save(new Payment(orderId, creditCard, LocalDate.now()));
    }

    @Override
    @Transactional
    public Receipt readReceipt(UUID orderId) {
        var order = orders.findOrderById(orderId);
        var payment = payments.findPaymentByOrderId(orderId);

        return new Receipt(order.getCost(), payment.getPaid());
    }

    @Override
    @Transactional
    public void startPreparingOrder(UUID orderId) {
        var order = orders.findOrderById(orderId);

        orders.save(order.markBeingPrepared());
    }

    @Override
    @Transactional
    public void finishPreparingOrder(UUID orderId) {
        var order = orders.findOrderById(orderId);

        orders.save(order.markPrepared());
    }

    @Override
    @Transactional
    public Order completeOrder(UUID orderId) {
        var order = orders.findOrderById(orderId);

        return orders.save(order.markTaken());
    }
}
