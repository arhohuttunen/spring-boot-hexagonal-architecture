package com.arhohuttunen.coffeeshop.adapter.in.rest.resource;

import com.arhohuttunen.coffeeshop.application.order.LineItem;
import com.arhohuttunen.coffeeshop.shared.Drink;
import com.arhohuttunen.coffeeshop.shared.Milk;
import com.arhohuttunen.coffeeshop.shared.Size;

public record LineItemResponse(Drink drink, Integer quantity, Milk milk, Size size) {
    public static LineItemResponse fromDomain(LineItem lineItem) {
        return new LineItemResponse(
                lineItem.drink(),
                lineItem.quantity(),
                lineItem.milk(),
                lineItem.size()
        );
    }
}
