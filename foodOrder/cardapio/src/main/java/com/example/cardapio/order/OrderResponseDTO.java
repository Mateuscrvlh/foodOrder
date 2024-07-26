package com.example.cardapio.order;

import com.example.cardapio.food.Food;

import java.util.stream.Collectors;

public record OrderResponseDTO() {

    public OrderResponseDTO(Order order) {
        this(order.getObservation(),
                order.getFood().stream()
                        .map(FoodResponseDTO::new)
                        .collect(Collectors.toList()));
    }

}
