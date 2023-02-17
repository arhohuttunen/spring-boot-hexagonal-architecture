package com.arhohuttunen.restbucks.application;

import com.arhohuttunen.restbucks.application.in.OrderingCoffee;
import com.arhohuttunen.restbucks.application.in.PreparingCoffee;
import com.arhohuttunen.restbucks.application.out.InMemoryOrders;
import com.arhohuttunen.restbucks.application.out.InMemoryPayments;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.arhohuttunen.restbucks.application.order.OrderTestFactory.anOrder;
import static com.arhohuttunen.restbucks.application.payment.CreditCardTestFactory.aCreditCard;
import static org.assertj.core.api.Assertions.assertThat;

class AcceptanceTests {
    private OrderingCoffee customer;
    private PreparingCoffee barista;

    @BeforeEach
    void setup() {
        var coffeeShop = new CoffeeShop(new InMemoryOrders(), new InMemoryPayments());
        customer = coffeeShop;
        barista = coffeeShop;
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
