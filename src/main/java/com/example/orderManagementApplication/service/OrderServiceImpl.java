package com.example.orderManagementApplication.service;

import com.example.orderManagementApplication.dto.OrderInputDto;
import com.example.orderManagementApplication.dto.OrderOutputDto;
import com.example.orderManagementApplication.entity.Coupon;
import com.example.orderManagementApplication.entity.Order;
import com.example.orderManagementApplication.entity.User;
import com.example.orderManagementApplication.entity.enums.OrderStatus;
import com.example.orderManagementApplication.exception.ResourceNotFoundException;
import com.example.orderManagementApplication.mapper.EntityDtoMapper;
import com.example.orderManagementApplication.repository.CouponRepository;
import com.example.orderManagementApplication.repository.OrderRepository;
import com.example.orderManagementApplication.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CouponRepository couponRepository;
    private final EntityDtoMapper entityDtoMapper;

    public OrderServiceImpl(UserRepository userRepository, OrderRepository orderRepository, CouponRepository couponRepository, EntityDtoMapper entityDtoMapper) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.couponRepository = couponRepository;
        this.entityDtoMapper = entityDtoMapper;
    }

    @Override
    public OrderOutputDto placeOrder(OrderInputDto inputDto) {
        User user = userRepository.findById(inputDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + inputDto.getUserId()));

        Set<Coupon> coupons = new HashSet<>();
        if (inputDto.getCouponIds() != null && !inputDto.getCouponIds().isEmpty()) {
            coupons = new HashSet<>(couponRepository.findAllById(inputDto.getCouponIds()));
        }

        Order order = Order.builder()
                .orderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .date(LocalDateTime.now())
                .orderStatus(OrderStatus.PENDING)
                .totalAmount(inputDto.getTotalAmount())
                .user(user)
                .coupons(coupons)
                .build();

        Order savedOrder = orderRepository.save(order);

        return entityDtoMapper.toOrderOutputDto(savedOrder);
    }

    @Override
    public OrderOutputDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        return entityDtoMapper.toOrderOutputDto(order);
    }

    @Override
    public List<OrderOutputDto> getOrdersByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        return user.getOrders().stream()
                .map(entityDtoMapper::toOrderOutputDto)
                .toList();

    }
}
