package com.arhohuttunen.restbucks.adapter.in.rest.resource;

import com.arhohuttunen.restbucks.application.order.OrderItem;
import com.arhohuttunen.restbucks.shared.Drink;
import com.arhohuttunen.restbucks.shared.Milk;
import com.arhohuttunen.restbucks.shared.Size;

public record OrderItemRequest(Drink drink, Integer quantity, Milk milk, Size size) {
    public OrderItem toDomain() {
        return new OrderItem(drink, quantity, milk, size);
    }
}
