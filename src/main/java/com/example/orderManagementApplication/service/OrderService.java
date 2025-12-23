package com.example.orderManagementApplication.service;

import com.example.orderManagementApplication.dto.OrderInputDto;
import com.example.orderManagementApplication.dto.OrderOutputDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderOutputDto placeOrder(OrderInputDto inputDto);

    OrderOutputDto getOrderById(Long id);

    List<OrderOutputDto> getOrdersByUserId(Long userId);
}
