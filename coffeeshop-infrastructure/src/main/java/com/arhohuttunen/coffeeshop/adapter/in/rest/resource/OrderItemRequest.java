package com.arhohuttunen.coffeeshop.adapter.in.rest.resource;

import com.arhohuttunen.coffeeshop.application.order.OrderItem;
import com.arhohuttunen.coffeeshop.shared.Drink;
import com.arhohuttunen.coffeeshop.shared.Milk;
import com.arhohuttunen.coffeeshop.shared.Size;

public record OrderItemRequest(Drink drink, Integer quantity, Milk milk, Size size) {
    public OrderItem toDomain() {
        return new OrderItem(drink, quantity, milk, size);
    }
}
