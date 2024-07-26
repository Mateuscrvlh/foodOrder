package com.example.cardapio.controller;

import com.example.cardapio.food.Food;
import com.example.cardapio.order.Order;
import com.example.cardapio.order.OrderRequestDTO;
import com.example.cardapio.order.OrderResponseDTO;
import com.example.cardapio.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public OrderResponseDTO saveOrder(@RequestBody OrderRequestDTO data) {
        Order orderData = new Order(data);
        Order savedOrder = orderRepository.save(orderData);
        return new OrderResponseDTO(savedOrder);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public OrderResponseDTO getOrderById(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return new OrderResponseDTO(order);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public OrderResponseDTO updateOrder(@PathVariable Long id, @RequestBody OrderRequestDTO data) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        existingOrder.setObservation(data.observation());
        existingOrder.getFood().clear();
        existingOrder.getFood().addAll(data.food().stream().map(Food::new).collect(Collectors.toList()));
        existingOrder.getFood().forEach(food -> food.setOrder(existingOrder));
        Order updatedOrder = orderRepository.save(existingOrder);
        return new OrderResponseDTO(updatedOrder);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
    }
}
