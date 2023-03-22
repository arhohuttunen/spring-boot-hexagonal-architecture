package com.arhohuttunen.coffeeshop.application.order;

import com.arhohuttunen.coffeeshop.shared.Drink;
import com.arhohuttunen.coffeeshop.shared.Location;
import com.arhohuttunen.coffeeshop.shared.Milk;
import com.arhohuttunen.coffeeshop.shared.Size;

import java.util.List;

public class OrderTestFactory {
    public static Order anOrder() {
        return new Order(Location.TAKE_AWAY, List.of(new LineItem(Drink.LATTE, 1, Milk.WHOLE, Size.LARGE)));
    }

    public static Order aPaidOrder() {
        return anOrder()
                .markPaid();
    }

    public static Order anOrderInPreparation() {
        return aPaidOrder()
                .markBeingPrepared();
    }

    public static Order aReadyOrder() {
        return anOrderInPreparation()
                .markPrepared();
    }
}
