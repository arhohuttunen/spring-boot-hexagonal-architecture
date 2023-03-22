package com.arhohuttunen.coffeeshop.application.order;

import com.arhohuttunen.coffeeshop.shared.Drink;
import com.arhohuttunen.coffeeshop.shared.Location;
import com.arhohuttunen.coffeeshop.shared.Milk;
import com.arhohuttunen.coffeeshop.shared.Size;
import com.arhohuttunen.coffeeshop.shared.Status;

import java.util.List;
import java.util.UUID;

public class OrderTestFactory {
    public static Order anOrder() {
        return new Order(Location.TAKE_AWAY, List.of(new LineItem(Drink.LATTE, 1, Milk.WHOLE, Size.LARGE)));
    }

    public static Order aReadyOrder() {
        return new Order(UUID.randomUUID(), Location.TAKE_AWAY, List.of(new LineItem(Drink.LATTE, 1, Milk.WHOLE, Size.LARGE)), Status.READY);
    }
}
