package com.example.cardapio.order;

import com.example.cardapio.food.Food;

public record OrderRequestDTO(String observation, Food food) {
}
