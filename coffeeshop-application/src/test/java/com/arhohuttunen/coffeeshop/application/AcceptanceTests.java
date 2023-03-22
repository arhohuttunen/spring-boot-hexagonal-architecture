package com.arhohuttunen.coffeeshop.application;

import com.arhohuttunen.coffeeshop.application.in.OrderingCoffee;
import com.arhohuttunen.coffeeshop.application.in.PreparingCoffee;
import com.arhohuttunen.coffeeshop.application.order.LineItem;
import com.arhohuttunen.coffeeshop.application.order.Order;
import com.arhohuttunen.coffeeshop.application.out.Orders;
import com.arhohuttunen.coffeeshop.application.out.Payments;
import com.arhohuttunen.coffeeshop.application.out.stubs.InMemoryOrders;
import com.arhohuttunen.coffeeshop.application.out.stubs.InMemoryPayments;
import com.arhohuttunen.coffeeshop.shared.Drink;
import com.arhohuttunen.coffeeshop.shared.Location;
import com.arhohuttunen.coffeeshop.shared.Milk;
import com.arhohuttunen.coffeeshop.shared.Size;
import com.arhohuttunen.coffeeshop.shared.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.arhohuttunen.coffeeshop.application.order.OrderTestFactory.aPaidOrder;
import static com.arhohuttunen.coffeeshop.application.order.OrderTestFactory.aReadyOrder;
import static com.arhohuttunen.coffeeshop.application.order.OrderTestFactory.anOrder;
import static com.arhohuttunen.coffeeshop.application.order.OrderTestFactory.anOrderInPreparation;
import static com.arhohuttunen.coffeeshop.application.payment.CreditCardTestFactory.aCreditCard;
import static com.arhohuttunen.coffeeshop.application.payment.PaymentTestFactory.aPaymentForOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AcceptanceTests {
    private Orders orders;
    private Payments payments;
    private OrderingCoffee customer;
    private PreparingCoffee barista;

    @BeforeEach
    void setup() {
        orders = new InMemoryOrders();
        payments = new InMemoryPayments();
        customer = new CoffeeShop(orders, payments);
        barista = new CoffeeMachine(orders);
    }

    @Test
    void customerCanUpdateTheOrderBeforePaying() {
        var orderWithOneItem = new Order(Location.TAKE_AWAY, List.of(new LineItem(Drink.LATTE, Milk.WHOLE, Size.LARGE, 1)));
        var orderWithTwoItems = new Order(Location.TAKE_AWAY, List.of(new LineItem(Drink.LATTE, Milk.WHOLE, Size.LARGE, 2)));

        var order = customer.createOrder(orderWithOneItem);
        var updatedOrder = customer.updateOrder(order.getId(), orderWithTwoItems);

        assertThat(updatedOrder.getItems()).containsExactly(new LineItem(Drink.LATTE, Milk.WHOLE, Size.LARGE, 2));
    }

    @Test
    void customerCanCancelTheOrderBeforePaying() {
        var existingOrder = orders.save(anOrder());

        customer.cancelOrder(existingOrder.getId());

        assertThat(orders.findOrderById(existingOrder.getId())).isNull();
    }

    @Test
    void noChangesAllowedWhenOrderIsPaid() {
        var existingOrder = orders.save(aPaidOrder());

        assertThatThrownBy(() -> customer.updateOrder(existingOrder.getId(), anOrder())).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void customerCanPayTheOrder() {
        var existingOrder = orders.save(anOrder());
        var creditCard = aCreditCard();

        var payment = customer.payOrder(existingOrder.getId(), creditCard);

        assertThat(payment.getOrderId()).isEqualTo(existingOrder.getId());
        assertThat(payment.getCreditCard()).isEqualTo(creditCard);
    }

    @Test
    void customerCanGetReceiptWhenOrderIsPaid() {
        var existingOrder = orders.save(aPaidOrder());
        var existingPayment = payments.save(aPaymentForOrder(existingOrder));

        var receipt = customer.readReceipt(existingOrder.getId());

        assertThat(receipt.amount()).isEqualTo(existingOrder.getCost());
        assertThat(receipt.paid()).isEqualTo(existingPayment.getPaid());
    }

    @Test
    void baristaCanStartPreparingTheOrderWhenItIsPaid() {
        var existingOrder = orders.save(aPaidOrder());

        barista.startPreparingOrder(existingOrder.getId());

        assertThat(existingOrder.getStatus()).isEqualTo(Status.PREPARING);
    }

    @Test
    void baristaCanMarkTheOrderReadyWhenSheIsFinishedPreparing() {
        var existingOrder = orders.save(anOrderInPreparation());

        barista.finishPreparingOrder(existingOrder.getId());

        assertThat(existingOrder.getStatus()).isEqualTo(Status.READY);
    }

    @Test
    void customerCanTakeTheOrderWhenItIsReady() {
        var existingOrder = orders.save(aReadyOrder());

        customer.completeOrder(existingOrder.getId());

        assertThat(existingOrder.getStatus()).isEqualTo(Status.TAKEN);
    }
}
