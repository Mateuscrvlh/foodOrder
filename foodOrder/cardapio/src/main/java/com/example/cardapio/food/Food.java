package com.example.cardapio.food;

import com.example.cardapio.order.Order;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "foods")
@Entity(name = "foods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "food_id")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long food_id;
    private String title;
    private String image;
    private Integer price;

    @Setter
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Food(FoodRequestDTO data) {
        this.image = data.image();
        this.title = data.title();
        this.price = data.price();
    }

}
