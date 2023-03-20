package com.arhohuttunen.coffeeshop.application;

import com.arhohuttunen.coffeeshop.application.order.Order;
import com.arhohuttunen.coffeeshop.application.order.OrderItem;
import com.arhohuttunen.coffeeshop.application.out.stubs.InMemoryOrders;
import com.arhohuttunen.coffeeshop.application.out.stubs.InMemoryPayments;
import com.arhohuttunen.coffeeshop.application.out.Orders;
import com.arhohuttunen.coffeeshop.application.out.Payments;
import com.arhohuttunen.coffeeshop.shared.Drink;
import com.arhohuttunen.coffeeshop.shared.Location;
import com.arhohuttunen.coffeeshop.shared.Milk;
import com.arhohuttunen.coffeeshop.shared.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static com.arhohuttunen.coffeeshop.application.order.OrderTestFactory.aPaidOrder;
import static com.arhohuttunen.coffeeshop.application.order.OrderTestFactory.aReadyOrder;
import static com.arhohuttunen.coffeeshop.application.order.OrderTestFactory.anOrder;
import static com.arhohuttunen.coffeeshop.application.order.OrderTestFactory.anOrderInPreparation;
import static com.arhohuttunen.coffeeshop.application.payment.CreditCardTestFactory.aCreditCard;
import static com.arhohuttunen.coffeeshop.application.payment.PaymentTestFactory.aPaymentForOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CoffeeShopTest {
    private Orders orders;
    private Payments payments;
    private CoffeeShop coffeeShop;

    private static Stream<Arguments> drinkCosts() {
        return Stream.of(
                arguments(1, Size.SMALL, BigDecimal.valueOf(4.0)),
                arguments(1, Size.LARGE, BigDecimal.valueOf(5.0)),
                arguments(2, Size.SMALL, BigDecimal.valueOf(8.0))
        );
    }

    @BeforeEach
    void setup() {
        orders = new InMemoryOrders();
        payments = new InMemoryPayments();
        coffeeShop = new CoffeeShop(orders, payments);
    }

    @Test
    void createdOrdersAreUnpaid() {
        var order = coffeeShop.createOrder(anOrder());

        assertThat(order.isUnpaid()).isTrue();
    }

    @ParameterizedTest(name = "{0} drinks of size {1} cost {2}")
    @MethodSource("drinkCosts")
    void creatingOrderReturnsCost(int quantity, Size size, BigDecimal expectedCost) {
        var order = coffeeShop.createOrder(
                new Order(Location.TAKE_AWAY, List.of(new OrderItem(Drink.LATTE, quantity, Milk.WHOLE, size)))
        );

        assertThat(order.getCost()).isEqualTo(expectedCost);
    }

    @Test
    void creatingOrderWithMultipleItemsReturnsCost() {
        var order = coffeeShop.createOrder(
                new Order(Location.TAKE_AWAY, List.of(
                        new OrderItem(Drink.LATTE, 1, Milk.SKIMMED, Size.LARGE),
                        new OrderItem(Drink.ESPRESSO, 1, Milk.SOY, Size.SMALL)
                ))
        );

        assertThat(order.getCost()).isEqualTo(BigDecimal.valueOf(9.0));
    }

    @Test
    void readingOrderReturnsDetails() {
        var existingOrder = new Order(Location.TAKE_AWAY, List.of(new OrderItem(Drink.LATTE, 1, Milk.WHOLE, Size.SMALL)));
        orders.save(existingOrder);

        var order = coffeeShop.readOrder(existingOrder.getId());

        assertThat(order.getLocation()).isEqualTo(Location.TAKE_AWAY);
        assertThat(order.getItems()).containsExactly(new OrderItem(Drink.LATTE, 1, Milk.WHOLE, Size.SMALL));
    }

    @Test
    void updatingOrderReturnsDetails() {
        var existingOrder = orders.save(anOrder());

        var order = coffeeShop.updateOrder(existingOrder.getId(),
                new Order(Location.IN_STORE, List.of(new OrderItem(Drink.ESPRESSO, 2, Milk.WHOLE, Size.SMALL)))
        );

        assertThat(order.getLocation()).isEqualTo(Location.IN_STORE);
        assertThat(order.getItems()).containsExactly(new OrderItem(Drink.ESPRESSO, 2, Milk.WHOLE, Size.SMALL));
    }

    @Test
    void cannotUpdatePaidOrder() {
        var existingOrder = orders.save(aPaidOrder());

        assertThatThrownBy(() -> coffeeShop.updateOrder(existingOrder.getId(), anOrder())).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void cancelingOrderDeletesIt() {
        var existingOrder = orders.save(anOrder());

        coffeeShop.cancelOrder(existingOrder.getId());

        assertThat(orders.findOrderById(existingOrder.getId())).isNull();
    }

    @Test
    void cannotCancelPaidOrder() {
        var existingOrder = orders.save(aPaidOrder());

        assertThatThrownBy(() -> coffeeShop.cancelOrder(existingOrder.getId())).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void payingOrderReturnsPaymentDetails() {
        var existingOrder = orders.save(anOrder());
        var creditCard = aCreditCard();

        var payment = coffeeShop.payOrder(existingOrder.getId(), creditCard);

        assertThat(payment.getOrderId()).isEqualTo(existingOrder.getId());
        assertThat(payment.getCreditCard()).isEqualTo(creditCard);
    }

    @Test
    void cannotPayAlreadyPaidOrder() {
        var existingOrder = orders.save(aPaidOrder());

        assertThatThrownBy(() -> coffeeShop.payOrder(existingOrder.getId(), aCreditCard())).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void readingReceiptReturnsAmountAndPaidDate() {
        var existingOrder = orders.save(anOrder());
        var existingPayment = payments.save(aPaymentForOrder(existingOrder));

        var receipt = coffeeShop.readReceipt(existingOrder.getId());

        assertThat(receipt.amount()).isEqualTo(existingOrder.getCost());
        assertThat(receipt.paid()).isEqualTo(existingPayment.getPaid());
    }

    @Test
    void completingOrderChangesOrderStatus() {
        var existingOrder = orders.save(aReadyOrder());

        var order = coffeeShop.completeOrder(existingOrder.getId());

        assertThat(order.isComplete()).isTrue();
    }

    @Test
    void cannotCompleteOrderThatIsNotReady() {
        var existingOrder = orders.save(anOrderInPreparation());

        assertThatThrownBy(() -> coffeeShop.completeOrder(existingOrder.getId())).isInstanceOf(IllegalStateException.class);
    }
}
