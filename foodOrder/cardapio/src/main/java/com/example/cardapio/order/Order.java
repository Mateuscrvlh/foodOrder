package com.example.cardapio.order;

import com.example.cardapio.food.Food;
import com.example.cardapio.food.FoodRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Table(name = "foods")
@Entity(name = "foods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "order_id")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;
    private String observation;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Food> food;

    // Construtor que aceita OrderRequestDTO
    public Order(OrderRequestDTO data) {
        this.observation = data.observation();
        this.food = data.food().stream()
                .map(Food::new)
                .collect(Collectors.toList());

        for (Food foodItem : this.food) {
            foodItem.setOrder(this);
        }
    }

}
