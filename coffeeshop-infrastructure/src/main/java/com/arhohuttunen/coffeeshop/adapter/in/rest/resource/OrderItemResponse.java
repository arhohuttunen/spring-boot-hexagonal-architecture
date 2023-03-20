package com.arhohuttunen.coffeeshop.adapter.in.rest.resource;

import com.arhohuttunen.coffeeshop.application.order.OrderItem;
import com.arhohuttunen.coffeeshop.shared.Drink;
import com.arhohuttunen.coffeeshop.shared.Milk;
import com.arhohuttunen.coffeeshop.shared.Size;

public record OrderItemResponse(Drink drink, Integer quantity, Milk milk, Size size) {
    public static OrderItemResponse fromDomain(OrderItem orderItem) {
        return new OrderItemResponse(orderItem.drink(), orderItem.quantity(), orderItem.milk(), orderItem.size());
    }
}
