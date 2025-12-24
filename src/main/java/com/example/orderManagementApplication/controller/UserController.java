package com.example.orderManagementApplication.controller;

import com.example.orderManagementApplication.dto.UserInputDto;
import com.example.orderManagementApplication.dto.UserOrderDto;
import com.example.orderManagementApplication.dto.UserOutputDto;
import com.example.orderManagementApplication.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserOutputDto> createUser(@Valid @RequestBody UserInputDto inputDto) {
        return new ResponseEntity<>(userService.createUser(inputDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserOutputDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserOutputDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}/orders-summary")
    public ResponseEntity<UserOrderDto> getUserOrdersSummary(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserWithOrders(id));
    }
}