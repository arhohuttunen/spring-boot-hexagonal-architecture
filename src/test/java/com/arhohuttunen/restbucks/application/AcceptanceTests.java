package com.arhohuttunen.restbucks.application;

import com.arhohuttunen.restbucks.application.out.InMemoryOrders;
import com.arhohuttunen.restbucks.application.out.InMemoryPayments;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.arhohuttunen.restbucks.application.order.OrderTestFactory.anOrder;
import static com.arhohuttunen.restbucks.application.payment.CreditCardTestFactory.aCreditCard;
import static org.assertj.core.api.Assertions.assertThat;

class AcceptanceTests {
    private CoffeeShop coffeeShop;

    @BeforeEach
    void setup() {
        coffeeShop = new CoffeeShop(new InMemoryOrders(), new InMemoryPayments());
    }

    @Test
    void processNewOrder() {
        var order = coffeeShop.createOrder(anOrder());
        coffeeShop.payOrder(order.getId(), aCreditCard());
        coffeeShop.startPreparingOrder(order.getId());
        coffeeShop.finishPreparingOrder(order.getId());
        coffeeShop.readReceipt(order.getId());
        var completedOrder = coffeeShop.completeOrder(order.getId());

        assertThat(completedOrder.isComplete()).isTrue();
    }

    @Test
    void cancelOrderBeforePayment() {
        var order = coffeeShop.createOrder(anOrder());
        coffeeShop.cancelOrder(order.getId());

        assertThat(coffeeShop.readOrder(order.getId())).isNull();
    }
}
