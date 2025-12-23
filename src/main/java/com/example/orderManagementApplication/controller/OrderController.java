package com.example.orderManagementApplication.controller;

import com.example.orderManagementApplication.dto.OrderInputDto;
import com.example.orderManagementApplication.dto.OrderOutputDto;
import com.example.orderManagementApplication.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderOutputDto> placeOrder(@Valid @RequestBody OrderInputDto inputDto) {
        return new ResponseEntity<>(orderService.placeOrder(inputDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderOutputDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderOutputDto>> getOrdersByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }
}