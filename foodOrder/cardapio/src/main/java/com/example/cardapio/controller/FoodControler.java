package com.example.cardapio.controller;

import com.example.cardapio.food.Food;
import com.example.cardapio.food.FoodRepository;
import com.example.cardapio.food.FoodRequestDTO;
import com.example.cardapio.food.FoodResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("food")
public class FoodControler {

    @Autowired
    private FoodRepository foodRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public FoodResponseDTO saveFood(@RequestBody FoodRequestDTO data) {
        Food foodData = new Food(data);
        Food savedFood = foodRepository.save(foodData);
        return new FoodResponseDTO(savedFood);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<FoodResponseDTO> getAllFood() {
        return foodRepository.findAll().stream()
                .map(FoodResponseDTO::new)
                .collect(Collectors.toList());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public FoodResponseDTO getFoodById(@PathVariable Long id) {
        Food food = foodRepository.findById(id).orElseThrow(() -> new RuntimeException("Food not found"));
        return new FoodResponseDTO(food);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public FoodResponseDTO updateFood(@PathVariable Long id, @RequestBody FoodRequestDTO data) {
        Food existingFood = foodRepository.findById(id).orElseThrow(() -> new RuntimeException("Food not found"));
        existingFood.setTitle(data.title());
        existingFood.setImage(data.image());
        existingFood.setPrice(data.price());
        Food updatedFood = foodRepository.save(existingFood);
        return new FoodResponseDTO(updatedFood);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void deleteFood(@PathVariable Long id) {
        foodRepository.deleteById(id);
    }
}
