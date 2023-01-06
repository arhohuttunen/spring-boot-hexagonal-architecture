package com.arhohuttunen.restbucks.adapter.in.rest.resource;

import com.arhohuttunen.restbucks.application.order.OrderItem;
import com.arhohuttunen.restbucks.shared.Drink;
import com.arhohuttunen.restbucks.shared.Milk;
import com.arhohuttunen.restbucks.shared.Size;

public record OrderItemResponse(Drink drink, Integer quantity, Milk milk, Size size) {
    public static OrderItemResponse fromDomain(OrderItem orderItem) {
        return new OrderItemResponse(orderItem.drink(), orderItem.quantity(), orderItem.milk(), orderItem.size());
    }
}
