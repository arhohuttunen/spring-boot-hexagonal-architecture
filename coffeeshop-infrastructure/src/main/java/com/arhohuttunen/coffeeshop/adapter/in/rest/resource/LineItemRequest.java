package com.arhohuttunen.coffeeshop.adapter.in.rest.resource;

import com.arhohuttunen.coffeeshop.application.order.LineItem;
import com.arhohuttunen.coffeeshop.shared.Drink;
import com.arhohuttunen.coffeeshop.shared.Milk;
import com.arhohuttunen.coffeeshop.shared.Size;

public record LineItemRequest(Drink drink, Integer quantity, Milk milk, Size size) {
    public LineItem toDomain() {
        return new LineItem(drink, quantity, milk, size);
    }
}
