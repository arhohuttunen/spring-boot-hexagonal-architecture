package com.arhohuttunen.coffeeshop.application;

import com.arhohuttunen.coffeeshop.application.in.OrderingCoffee;
import com.arhohuttunen.coffeeshop.application.in.PreparingCoffee;
import com.arhohuttunen.coffeeshop.application.out.stubs.InMemoryOrders;
import com.arhohuttunen.coffeeshop.application.out.stubs.InMemoryPayments;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.arhohuttunen.coffeeshop.application.order.OrderTestFactory.anOrder;
import static com.arhohuttunen.coffeeshop.application.payment.CreditCardTestFactory.aCreditCard;
import static org.assertj.core.api.Assertions.assertThat;

class AcceptanceTests {
    private OrderingCoffee customer;
    private PreparingCoffee barista;

    @BeforeEach
    void setup() {
        var orders = new InMemoryOrders();
        var payments = new InMemoryPayments();
        customer = new CoffeeShop(orders, payments);
        barista = new CoffeeMachine(orders);
    }

    @Test
    void processNewOrder() {
        var order = customer.createOrder(anOrder());
        customer.payOrder(order.getId(), aCreditCard());
        barista.startPreparingOrder(order.getId());
        barista.finishPreparingOrder(order.getId());
        customer.readReceipt(order.getId());
        var completedOrder = customer.completeOrder(order.getId());

        assertThat(completedOrder.isComplete()).isTrue();
    }

    @Test
    void cancelOrderBeforePayment() {
        var order = customer.createOrder(anOrder());
        customer.cancelOrder(order.getId());

        assertThat(customer.readOrder(order.getId())).isNull();
    }
}
